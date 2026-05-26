package com.banking;

import com.banking.exception.*;
import com.banking.filehandler.FileHandler;
import com.banking.model.*;

import java.math.BigDecimal;
import java.util.Scanner;

public class BankingApp {

    private static Bank bank = new Bank("Java National Bank");
    private static Scanner scanner = new Scanner(System.in);
    private static int accountCounter = 1;

    public static void main(String[] args) {
        System.out.println("================================");
        System.out.println("   JAVA NATIONAL BANK");
        System.out.println("================================");

        // Load existing accounts from file
        for (Account a : FileHandler.loadAccounts()) {
            bank.addAccount(a);
        }

        boolean running = true;
        while (running) {
            printMenu();
            int choice = readInt("Enter choice: ");
            switch (choice) {
                case 1 -> createAccount();
                case 2 -> deposit();
                case 3 -> withdraw();
                case 4 -> viewBalance();
                case 5 -> viewTransactions();
                case 6 -> viewAllAccounts();
                case 7 -> applyInterest();
                case 0 -> {
                    FileHandler.saveAccounts(bank.getAllAccounts());
                    System.out.println("Goodbye!");
                    running = false;
                }
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    static void printMenu() {
        System.out.println("\n--- MENU ---");
        System.out.println("1. Create account");
        System.out.println("2. Deposit");
        System.out.println("3. Withdraw");
        System.out.println("4. View balance");
        System.out.println("5. View transaction history");
        System.out.println("6. View all accounts");
        System.out.println("7. Apply interest");
        System.out.println("0. Exit");
    }

    static void createAccount() {
        System.out.println("Account type: 1=Savings  2=Current  3=Fixed Deposit");
        int type = readInt("Choice: ");
        System.out.print("Account holder name: ");
        String name = scanner.nextLine();
        BigDecimal amount = readAmount("Initial deposit: ");
        String number = "ACC" + String.format("%03d", accountCounter++);

        try {
            Account account = switch (type) {
                case 1 -> new SavingsAccount(number, name, amount);
                case 2 -> new CurrentAccount(number, name, amount);
                case 3 -> {
                    int months = readInt("Term in months: ");
                    yield new FixedDepositAccount(number, name, amount, months);
                }
                default -> throw new IllegalArgumentException("Invalid type");
            };
            bank.addAccount(account);
            FileHandler.saveAccounts(bank.getAllAccounts());
            System.out.println("Account created: " + number);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    static void deposit() {
        String number = readAccountNumber();
        try {
            Account account = bank.getAccount(number);
            BigDecimal amount = readAmount("Amount: ");
            account.deposit(amount);
            FileHandler.saveAccounts(bank.getAllAccounts());
        } catch (BankingException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    static void withdraw() {
        String number = readAccountNumber();
        try {
            Account account = bank.getAccount(number);
            BigDecimal amount = readAmount("Amount: ");
            account.withdraw(amount);
            FileHandler.saveAccounts(bank.getAllAccounts());
        } catch (BankingException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    static void viewBalance() {
        String number = readAccountNumber();
        try {
            Account account = bank.getAccount(number);
            account.printStatement();
        } catch (BankingException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    static void viewTransactions() {
        String number = readAccountNumber();
        try {
            bank.getAccount(number).printTransactionHistory();
        } catch (BankingException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    static void viewAllAccounts() {
        bank.printAllAccounts();
    }

    static void applyInterest() {
        InterestCalculator.applyMonthlyInterest(bank.getAllAccounts());
        FileHandler.saveAccounts(bank.getAllAccounts());
    }

    static String readAccountNumber() {
        System.out.print("Account number: ");
        return scanner.nextLine().trim().toUpperCase();
    }

    static int readInt(String prompt) {
        System.out.print(prompt);
        try {
            int value = Integer.parseInt(scanner.nextLine().trim());
            return value;
        } catch (NumberFormatException e) {
            System.out.println("Invalid number. Defaulting to 0.");
            return 0;
        }
    }

    static BigDecimal readAmount(String prompt) {
        System.out.print(prompt);
        try {
            return new BigDecimal(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Invalid amount. Defaulting to 0.");
            return BigDecimal.ZERO;
        }
    }
}