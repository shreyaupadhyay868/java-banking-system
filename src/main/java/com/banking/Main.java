package com.banking;

import com.banking.exception.*;
import com.banking.model.Account;
import com.banking.model.CurrentAccount;
import com.banking.model.FixedDepositAccount;
import com.banking.model.SavingsAccount;
import com.banking.filehandler.FileHandler;

import java.math.BigDecimal;

public class Main {

    public static void main(String[] args) {

        Bank bank = new Bank("Java National Bank");

        SavingsAccount savings = new SavingsAccount(
            "SAV001", "Shreya Upadhyay", new BigDecimal("5000.00"));
        CurrentAccount current = new CurrentAccount(
            "CUR001", "Rahul Sharma", new BigDecimal("3000.00"));
        FixedDepositAccount fd = new FixedDepositAccount(
            "FD001", "Priya Patel", new BigDecimal("50000.00"), 12);

        bank.addAccount(savings);
        bank.addAccount(current);
        bank.addAccount(fd);

        System.out.println("\n=== OPERATIONS ===");
        savings.deposit(new BigDecimal("2000.00"));
        savings.withdraw(new BigDecimal("1000.00"));
        savings.applyInterest();
        current.withdraw(new BigDecimal("5000.00"));

        System.out.println("\n=== ALL ACCOUNTS ===");
        bank.printAllAccounts();

        System.out.println("\n=== TRANSACTION HISTORY ===");
        savings.printTransactionHistory();

        System.out.println("\n=== TESTING AccountNotFoundException ===");
        try {
            bank.getAccount("INVALID999");
        } catch (AccountNotFoundException e) {
            System.out.println("Caught: " + e.getMessage());
        }

        System.out.println("\n=== TESTING PrematureWithdrawalException ===");
        try {
            fd.withdraw(new BigDecimal("1000.00"));
        } catch (PrematureWithdrawalException e) {
            System.out.println("Caught: " + e.getMessage());
        }

        System.out.println("\n=== POLYMORPHISM DEMO ===");
        for (Account account : bank.getAllAccounts()) {
            System.out.println(account.getAccountType()
                + " | " + account.getAccountHolder()
                + " | Balance: " + account.getBalance()
                + " | Interest: " + account.calculateInterest());
        }

        System.out.println("\n=== SAVING TO FILE ===");
        FileHandler.saveAccounts(bank.getAllAccounts());

        System.out.println("\n=== LOADING FROM FILE ===");
        for (Account a : FileHandler.loadAccounts()) {
            System.out.println("Loaded: " + a.getAccountType()
                + " | " + a.getAccountHolder()
                + " | " + a.getBalance());
        }

        System.out.println("\n=== PHASE 5: MONTHLY INTEREST ===");
        InterestCalculator.applyMonthlyInterest(bank.getAllAccounts());

        InterestCalculator.compareInterestTypes(
            new BigDecimal("10000.00"),
            new BigDecimal("0.07"),
            10
        );

        System.out.println("\n=== BALANCE PROJECTIONS ===");
        System.out.println("SAV001 in 5 years: " + InterestCalculator.projectBalance(
            savings.getBalance(), SavingsAccount.ANNUAL_INTEREST_RATE, 5));
        System.out.println("FD001 in 1 year  : " + InterestCalculator.projectBalance(
            fd.getBalance(), FixedDepositAccount.ANNUAL_INTEREST_RATE, 1));
    }
}