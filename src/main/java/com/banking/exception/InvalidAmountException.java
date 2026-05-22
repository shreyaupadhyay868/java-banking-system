package com.banking.exception;

import java.math.BigDecimal;

public class InvalidAmountException extends BankingException {

    private final BigDecimal amount;

    public InvalidAmountException(BigDecimal amount) {
        super(String.format("Invalid amount: %.2f. Amount must be positive.", amount));
        this.amount = amount;
    }

    public BigDecimal getAmount() { return amount; }
}
