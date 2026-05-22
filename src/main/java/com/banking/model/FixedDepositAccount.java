
package com.banking.model;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.time.LocalDate;
import com.banking.exception.PrematureWithdrawalException;

public class FixedDepositAccount extends Account {

    public static final BigDecimal ANNUAL_INTEREST_RATE = new BigDecimal("0.07");
    private final BigDecimal principal;
    private final int termMonths;
    private final LocalDate maturityDate;

    public FixedDepositAccount(String accountNumber, String accountHolder,
                               BigDecimal depositAmount, int termMonths) {
        super(accountNumber, accountHolder, depositAmount);
        if (termMonths <= 0) {
            throw new IllegalArgumentException("Term must be at least 1 month");
        }
        if (depositAmount.compareTo(new BigDecimal("1000.00")) < 0) {
            throw new IllegalArgumentException("Minimum FD amount is 1000.00");
        }
        this.principal = depositAmount;
        this.termMonths = termMonths;
        this.maturityDate = LocalDate.now().plusMonths(termMonths);
    }

    @Override
    public void withdraw(BigDecimal amount) {
        if (LocalDate.now().isBefore(maturityDate)) {
           throw new PrematureWithdrawalException(maturityDate);
        }
        super.withdraw(amount);
    }

    @Override
    public BigDecimal calculateInterest() {
        double p = principal.doubleValue();
        double r = ANNUAL_INTEREST_RATE.doubleValue();
        double t = (double) termMonths / 12.0;
        double interest = p * Math.pow(1 + r / 12, 12 * t) - p;
        return new BigDecimal(interest)
                .round(new MathContext(10))
                .setScale(2, RoundingMode.HALF_UP);
    }

    @Override
    public String getAccountType() { return "Fixed Deposit Account"; }

    public LocalDate getMaturityDate() { return maturityDate; }
    public BigDecimal getPrincipal() { return principal; }
    public int getTermMonths() { return termMonths; }
}