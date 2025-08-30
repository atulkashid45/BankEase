package com.bankease.controller;

import com.bankease.dto.AmountRequest;
import com.bankease.dto.TransferRequest;
import com.bankease.model.Account;
import com.bankease.model.Transaction;
import com.bankease.model.User;
import com.bankease.service.AccountService;
import com.bankease.service.CustomUserDetailsService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/me")
    public List<Account> myAccounts(@AuthenticationPrincipal User user) {
        return accountService.myAccounts(user);
    }

    @PostMapping
    public Account create(@AuthenticationPrincipal User user) {
        return accountService.createAccount(user);
    }

    @GetMapping("/{accountId}/transactions")
    public List<Transaction> transactions(@PathVariable Long accountId, @AuthenticationPrincipal User user) {
        return accountService.transactions(accountId, user);
    }

    @PostMapping("/deposit")
    public Account deposit(@RequestBody AmountRequest req, @AuthenticationPrincipal User user) {
        return accountService.deposit(req, user);
    }

    @PostMapping("/withdraw")
    public Account withdraw(@RequestBody AmountRequest req, @AuthenticationPrincipal User user) {
        return accountService.withdraw(req, user);
    }

    @PostMapping("/transfer")
    public ResponseEntity<?> transfer(@RequestBody TransferRequest req, @AuthenticationPrincipal User user) {
        accountService.transfer(req, user);
        return ResponseEntity.ok().build();
    }
}
