package com.banking.filehandler;

import com.banking.model.Account;
import com.banking.model.SavingsAccount;
import com.banking.model.CurrentAccount;
import com.banking.model.FixedDepositAccount;

import java.io.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class FileHandler {

    private static final String FILE_PATH = "data/accounts.csv";

    public static void saveAccounts(List<Account> accounts) {
        new File("data").mkdirs();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            writer.write("type,accountNumber,accountHolder,balance");
            writer.newLine();
            for (Account account : accounts) {
                String type = account.getAccountType();
                writer.write(type + "," 
                        + account.getAccountNumber() + ","
                        + account.getAccountHolder() + ","
                        + account.getBalance());
                writer.newLine();
            }
            System.out.println("Accounts saved to " + FILE_PATH);
        } catch (IOException e) {
            System.out.println("Error saving accounts: " + e.getMessage());
        }
    }

    public static List<Account> loadAccounts() {
        List<Account> accounts = new ArrayList<>();
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            System.out.println("No saved data found. Starting fresh.");
            return accounts;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            reader.readLine(); // skip header
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String type = parts[0];
                String accountNumber = parts[1];
                String accountHolder = parts[2];
                BigDecimal balance = new BigDecimal(parts[3]);

                Account account = switch (type) {
                    case "Savings Account" -> new SavingsAccount(
                            accountNumber, accountHolder, balance);
                    case "Current Account" -> new CurrentAccount(
                            accountNumber, accountHolder, balance);
                    case "Fixed Deposit Account" -> new FixedDepositAccount(
                            accountNumber, accountHolder, balance, 12);
                    default -> null;
                };
                if (account != null) accounts.add(account);
            }
            System.out.println("Loaded " + accounts.size() + " accounts from file.");
        } catch (IOException e) {
            System.out.println("Error loading accounts: " + e.getMessage());
        }
        return accounts;
    }
}