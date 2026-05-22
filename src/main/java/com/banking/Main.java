package com.banking;

import com.banking.model.Account;
import com.banking.model.CurrentAccount;
import com.banking.model.FixedDepositAccount;
import com.banking.model.SavingsAccount;

import java.math.BigDecimal;
import com.banking.exception.*;

public class Main {

    public static void main(String[] args) {

        System.out.println("=== SAVINGS ACCOUNT ===");
        SavingsAccount savings = new SavingsAccount(
            "SAV001", "Shreya Upadhyay", new BigDecimal("5000.00"));
        savings.printStatement();
        savings.deposit(new BigDecimal("2000.00"));
        try{
        savings.withdraw(new BigDecimal("9000.00"));
        }  catch (InsufficientFundsException e) {
        System.out.println("Caught: " + e.getMessage());
               }
        System.out.println("Interest: " + savings.calculateInterest());

        System.out.println("\n=== CURRENT ACCOUNT ===");
        CurrentAccount current = new CurrentAccount(
            "CUR001", "Rahul Sharma", new BigDecimal("3000.00"));
        current.printStatement();
        current.withdraw(new BigDecimal("5000.00"));
        System.out.println("Interest: " + current.calculateInterest());

        System.out.println("\n=== FIXED DEPOSIT ACCOUNT ===");
        FixedDepositAccount fd = new FixedDepositAccount(
            "FD001", "Priya Patel", new BigDecimal("50000.00"), 12);
        fd.printStatement();
        System.out.println("Interest: " + fd.calculateInterest());
        System.out.println("Maturity Date: " + fd.getMaturityDate());
        System.out.println("\n[Testing PrematureWithdrawalException]");
        try {
            fd.withdraw(new BigDecimal("1000.00"));
        } catch (PrematureWithdrawalException e) {
            System.out.println("Caught: " + e.getMessage());
        }

        System.out.println("\n=== POLYMORPHISM DEMO ===");
        Account[] accounts = { savings, current, fd };
        for (Account account : accounts) {
            System.out.println(account.getAccountType() +
                " | " + account.getAccountHolder() +
                " | Balance: " + account.getBalance() +
                " | Interest: " + account.calculateInterest());
        }
    }
}