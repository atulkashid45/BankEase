package com.bankease.dto;

import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public class TransferRequest {
    @NotNull public Long fromAccountId;
    @NotNull public Long toAccountId;
    @NotNull public BigDecimal amount;
    public String description;
}
