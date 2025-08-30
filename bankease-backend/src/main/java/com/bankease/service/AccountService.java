package com.bankease.service;

import com.bankease.dto.AmountRequest;
import com.bankease.dto.TransferRequest;
import com.bankease.exception.InsufficientFundsException;
import com.bankease.exception.NotFoundException;
import com.bankease.model.*;
import com.bankease.repository.AccountRepository;
import com.bankease.repository.TransactionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Random;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    public AccountService(AccountRepository accountRepository, TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }

    public List<Account> myAccounts(User owner) {
        return accountRepository.findByOwner(owner);
    }

    public Account createAccount(User owner) {
        String accNo = "BE" + (10000000 + new Random().nextInt(90000000));
        Account acc = new Account(accNo, owner);
        return accountRepository.save(acc);
    }

    public List<Transaction> transactions(Long accountId, User owner) {
        Account acc = accountRepository.findById(accountId).orElseThrow(() -> new NotFoundException("Account not found"));
        if (!acc.getOwner().getId().equals(owner.getId())) {
            throw new NotFoundException("Account not found");
        }
        return transactionRepository.findByAccountOrderByTimestampDesc(acc);
    }

    @Transactional
    public Account deposit(AmountRequest req, User owner) {
        Account acc = accountRepository.findById(req.accountId).orElseThrow(() -> new NotFoundException("Account not found"));
        if (!acc.getOwner().getId().equals(owner.getId())) throw new NotFoundException("Account not found");

        acc.setBalance(acc.getBalance().add(req.amount));
        accountRepository.save(acc);

        Transaction t = new Transaction(acc, TransactionType.CREDIT, req.amount, req.description == null ? "Deposit" : req.description);
        transactionRepository.save(t);
        return acc;
    }

    @Transactional
    public Account withdraw(AmountRequest req, User owner) {
        Account acc = accountRepository.findById(req.accountId).orElseThrow(() -> new NotFoundException("Account not found"));
        if (!acc.getOwner().getId().equals(owner.getId())) throw new NotFoundException("Account not found");

        if (acc.getBalance().compareTo(req.amount) < 0) throw new InsufficientFundsException("Insufficient funds");

        acc.setBalance(acc.getBalance().subtract(req.amount));
        accountRepository.save(acc);

        Transaction t = new Transaction(acc, TransactionType.DEBIT, req.amount, req.description == null ? "Withdrawal" : req.description);
        transactionRepository.save(t);
        return acc;
    }

    @Transactional
    public void transfer(TransferRequest req, User owner) {
        Account from = accountRepository.findById(req.fromAccountId).orElseThrow(() -> new NotFoundException("From account not found"));
        Account to = accountRepository.findById(req.toAccountId).orElseThrow(() -> new NotFoundException("To account not found"));
        if (!from.getOwner().getId().equals(owner.getId())) throw new NotFoundException("From account not found");

        if (from.getBalance().compareTo(req.amount) < 0) throw new InsufficientFundsException("Insufficient funds");

        from.setBalance(from.getBalance().subtract(req.amount));
        to.setBalance(to.getBalance().add(req.amount));
        accountRepository.save(from);
        accountRepository.save(to);

        transactionRepository.save(new Transaction(from, TransactionType.DEBIT, req.amount, req.description == null ? "Transfer out" : req.description));
        transactionRepository.save(new Transaction(to, TransactionType.CREDIT, req.amount, req.description == null ? "Transfer in" : req.description));
    }
}
