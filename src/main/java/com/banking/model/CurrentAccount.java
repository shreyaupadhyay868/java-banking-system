package com.banking.model;

import java.math.BigDecimal;

public class CurrentAccount extends Account {

    public static final BigDecimal DEFAULT_OVERDRAFT_LIMIT = new BigDecimal("10000.00");
    private final BigDecimal overdraftLimit;

    public CurrentAccount(String accountNumber, String accountHolder, BigDecimal initialDeposit) {
        super(accountNumber, accountHolder, initialDeposit);
        this.overdraftLimit = DEFAULT_OVERDRAFT_LIMIT;
    }

    @Override
    public void withdraw(BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Withdrawal amount must be positive");
        }
        BigDecimal effectiveLimit = getBalance().add(overdraftLimit);
        if (amount.compareTo(effectiveLimit) > 0) {
            throw new IllegalStateException(
                "Exceeds overdraft limit. Max withdrawable: " + effectiveLimit
            );
        }
        deductBalance(amount);
    }

    @Override
    public BigDecimal calculateInterest() {
        return BigDecimal.ZERO;
    }

    @Override
    public String getAccountType() {
        return "Current Account";
    }

    public BigDecimal getOverdraftLimit() { return overdraftLimit; }
}