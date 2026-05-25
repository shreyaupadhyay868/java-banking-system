package com.banking;

import com.banking.exception.AccountNotFoundException;
import com.banking.model.Account;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Bank {

    private final String name;
    private final Map<String, Account> accounts = new HashMap<>();

    public Bank(String name) {
        this.name = name;
    }

    public void addAccount(Account account) {
        accounts.put(account.getAccountNumber(), account);
        System.out.println("Account added: " + account.getAccountNumber()
                + " | " + account.getAccountHolder());
    }

    public Account getAccount(String accountNumber) {
        Account account = accounts.get(accountNumber);
        if (account == null) {
            throw new AccountNotFoundException(accountNumber);
        }
        return account;
    }

    public List<Account> getAllAccounts() {
        return new ArrayList<>(accounts.values());
    }

    public void printAllAccounts() {
        System.out.println("=== " + name + " — All Accounts ===");
        for (Account account : accounts.values()) {
            System.out.println(account.getAccountType()
                    + " | " + account.getAccountNumber()
                    + " | " + account.getAccountHolder()
                    + " | Balance: " + account.getBalance());
        }
    }

    public String getName() { return name; }
    public int getTotalAccounts() { return accounts.size(); }
}