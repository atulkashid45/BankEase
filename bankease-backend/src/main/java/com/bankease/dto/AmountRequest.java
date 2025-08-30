package com.bankease.dto;

import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public class AmountRequest {
    @NotNull public Long accountId;
    @NotNull public BigDecimal amount;
    public String description;
}
