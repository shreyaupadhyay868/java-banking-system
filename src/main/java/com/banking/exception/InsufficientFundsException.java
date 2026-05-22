package com.banking.exception;

import java.math.BigDecimal;

public class InsufficientFundsException extends BankingException {

    private final BigDecimal balance;
    private final BigDecimal requested;

    public InsufficientFundsException(BigDecimal balance, BigDecimal requested) {
        super(String.format("Insufficient funds. Balance: %.2f, Requested: %.2f",
                balance, requested));
        this.balance = balance;
        this.requested = requested;
    }

    public BigDecimal getBalance() { return balance; }
    public BigDecimal getRequested() { return requested; }
}