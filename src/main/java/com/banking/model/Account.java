package com.banking.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import com.banking.exception.InvalidAmountException;
import com.banking.exception.InsufficientFundsException;

public abstract class Account {

    private final String accountNumber;
    private final String accountHolder;
    private BigDecimal balance;
    private final List<Transaction> transactions = new ArrayList<>();

    protected Account(String accountNumber, String accountHolder, BigDecimal initialDeposit) {
        this.accountNumber = accountNumber;
        this.accountHolder = accountHolder;
        this.balance = initialDeposit.setScale(2, RoundingMode.HALF_UP);
    }

    public abstract BigDecimal calculateInterest();
    public abstract String getAccountType();

    public void deposit(BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidAmountException(amount);
        }
        this.balance = this.balance.add(amount).setScale(2, RoundingMode.HALF_UP);
        System.out.println("Deposited: " + amount + " | New Balance: " + this.balance);
        transactions.add(new Transaction(
            Transaction.Type.DEPOSIT, amount, this.balance, "Deposit"));
    }

    public void withdraw(BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidAmountException(amount);
        }
        if (amount.compareTo(this.balance) > 0) {
            throw new InsufficientFundsException(this.balance, amount);
        }
        deductBalance(amount);
    }

    protected void deductBalance(BigDecimal amount) {
        this.balance = this.balance.subtract(amount).setScale(2, RoundingMode.HALF_UP);
        System.out.println("Withdrew: " + amount + " | New Balance: " + this.balance);
        transactions.add(new Transaction(
            Transaction.Type.WITHDRAWAL, amount, this.balance, "Withdrawal"));
    }

    public void applyInterest() {
        BigDecimal interest = calculateInterest();
        if (interest.compareTo(BigDecimal.ZERO) > 0) {
            this.balance = this.balance.add(interest).setScale(2, RoundingMode.HALF_UP);
            System.out.println("Interest applied: " + interest + " | New Balance: " + this.balance);
            transactions.add(new Transaction(
                Transaction.Type.INTEREST, interest, this.balance, "Interest applied"));
        }
    }

    public void printTransactionHistory() {
        System.out.println("=== Transaction History: " + accountNumber + " ===");
        if (transactions.isEmpty()) {
            System.out.println("No transactions yet.");
            return;
        }
        for (Transaction t : transactions) {
            System.out.println(t);
        }
    }

    public List<Transaction> getTransactions() {
        return Collections.unmodifiableList(transactions);
    }

    public BigDecimal getBalance() { return balance; }
    public String getAccountNumber() { return accountNumber; }
    public String getAccountHolder() { return accountHolder; }

    public void printStatement() {
        System.out.println("Account Type   : " + getAccountType());
        System.out.println("Account Number : " + accountNumber);
        System.out.println("Holder         : " + accountHolder);
        System.out.println("Balance        : " + balance);
    }
}