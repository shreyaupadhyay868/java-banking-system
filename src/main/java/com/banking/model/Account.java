package com.banking.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import com.banking.exception.InvalidAmountException;
import com.banking.exception.InsufficientFundsException;

public abstract class Account {

    private final String accountNumber;
    private final String accountHolder;
    private BigDecimal balance;

    protected Account(String accountNumber, String accountHolder, BigDecimal initialDeposit) {
        this.accountNumber = accountNumber;
        this.accountHolder = accountHolder;
        this.balance = initialDeposit.setScale(2, RoundingMode.HALF_UP);
    }

    public abstract BigDecimal calculateInterest();
    public abstract String getAccountType();

    public BigDecimal getBalance() { return balance; }
    public String getAccountNumber() { return accountNumber; }
    public String getAccountHolder() { return accountHolder; }

    public void deposit(BigDecimal amount) {
    if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
       throw new InvalidAmountException(amount);

    }
    this.balance = this.balance.add(amount).setScale(2, RoundingMode.HALF_UP);
    System.out.println("Deposited: " + amount + " | New Balance: " + this.balance);
}

public void withdraw(BigDecimal amount) {
    if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
       throw new InvalidAmountException(amount);
    }
    if (amount.compareTo(this.balance) > 0) {
       throw new InsufficientFundsException(getBalance(), amount);
    }
    this.balance = this.balance.subtract(amount).setScale(2, RoundingMode.HALF_UP);
    System.out.println("Withdrew: " + amount + " | New Balance: " + this.balance);
}

protected void deductBalance(BigDecimal amount) {
    this.balance = this.balance.subtract(amount).setScale(2, RoundingMode.HALF_UP);
    System.out.println("Withdrew: " + amount + " | New Balance: " + this.balance);
}

public void printStatement() {
    System.out.println("Account Type   : " + getAccountType());
    System.out.println("Account Number : " + accountNumber);
    System.out.println("Holder         : " + accountHolder);
    System.out.println("Balance        : " + balance);
}

}