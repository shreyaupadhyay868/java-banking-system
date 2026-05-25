package com.banking;

import com.banking.model.Account;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class InterestCalculator {

    /**
     * Apply interest to all accounts in the bank.
     * Current accounts earn no interest — calculateInterest() returns 0 for them.
     */
    public static void applyMonthlyInterest(List<Account> accounts) {
        System.out.println("=== APPLYING MONTHLY INTEREST ===");
        for (Account account : accounts) {
            BigDecimal interest = calculateMonthlyInterest(account);
            if (interest.compareTo(BigDecimal.ZERO) > 0) {
                account.applyInterest();
                System.out.println(account.getAccountType()
                        + " | " + account.getAccountHolder()
                        + " | Monthly Interest: " + interest);
            } else {
                System.out.println(account.getAccountType()
                        + " | " + account.getAccountHolder()
                        + " | No interest applicable");
            }
        }
    }

    /**
     * Monthly interest = Annual interest / 12
     * Uses BigDecimal throughout — no double arithmetic.
     */
    public static BigDecimal calculateMonthlyInterest(Account account) {
        BigDecimal annualInterest = account.calculateInterest();
        return annualInterest
                .divide(new BigDecimal("12"), 2, RoundingMode.HALF_UP);
    }

    /**
     * Project future balance with compound interest.
     * Formula: A = P * (1 + r/n)^(n*t)
     */
    public static BigDecimal projectBalance(BigDecimal principal,
                                            BigDecimal annualRate,
                                            int years) {
        double p = principal.doubleValue();
        double r = annualRate.doubleValue();
        double amount = p * Math.pow(1 + r / 12, 12 * years);
        return new BigDecimal(amount).setScale(2, RoundingMode.HALF_UP);
    }

    /**
     * Compare simple vs compound interest for a given principal.
     */
    public static void compareInterestTypes(BigDecimal principal,
                                            BigDecimal rate,
                                            int years) {
        System.out.println("\n=== SIMPLE vs COMPOUND INTEREST ===");
        System.out.println("Principal : " + principal);
        System.out.println("Rate      : " + rate.multiply(new BigDecimal("100")) + "%");
        System.out.println("Years     : " + years);

        BigDecimal simple = principal
                .multiply(rate)
                .multiply(new BigDecimal(years))
                .setScale(2, RoundingMode.HALF_UP);

        BigDecimal compounded = projectBalance(principal, rate, years)
                .subtract(principal)
                .setScale(2, RoundingMode.HALF_UP);

        System.out.println("Simple interest   : " + simple);
        System.out.println("Compound interest : " + compounded);
        System.out.println("Difference        : "
                + compounded.subtract(simple).setScale(2, RoundingMode.HALF_UP));
    }
}