
package com.banking.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import com.banking.exception.InvalidAmountException;
import com.banking.exception.InsufficientFundsException;

public class SavingsAccount extends Account {

    public static final BigDecimal MINIMUM_BALANCE = new BigDecimal("500.00");
    public static final BigDecimal ANNUAL_INTEREST_RATE = new BigDecimal("0.04");

    public SavingsAccount(String accountNumber, String accountHolder, BigDecimal initialDeposit) {
        super(accountNumber, accountHolder, initialDeposit);

        if (initialDeposit.compareTo(MINIMUM_BALANCE) < 0) {
            throw new IllegalArgumentException(
                "Minimum opening deposit is 500.00. Got: " + initialDeposit
            );
        }
    }

    @Override
    public void withdraw(BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
           throw new InvalidAmountException(amount); 
        }
        BigDecimal balanceAfter = getBalance().subtract(amount);
        if (balanceAfter.compareTo(MINIMUM_BALANCE) < 0) {
           throw new InsufficientFundsException(getBalance(), amount);
            
        }
        super.withdraw(amount);
    }

    @Override
    public BigDecimal calculateInterest() {
        return getBalance()
                .multiply(ANNUAL_INTEREST_RATE)
                .setScale(2, RoundingMode.HALF_UP);
    }

    @Override
    public String getAccountType() {
        return "Savings Account";
    }
}