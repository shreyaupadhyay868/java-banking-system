package com.banking;

import com.banking.exception.*;
import com.banking.model.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import java.math.BigDecimal;

public class BankingSystemTest {

    private SavingsAccount savings;
    private CurrentAccount current;
    private FixedDepositAccount fd;

    @BeforeEach
    void setUp() {
        savings = new SavingsAccount("SAV001", "Test User",
                new BigDecimal("1000.00"));
        current = new CurrentAccount("CUR001", "Test User",
                new BigDecimal("1000.00"));
        fd = new FixedDepositAccount("FD001", "Test User",
                new BigDecimal("5000.00"), 12);
    }

    @Test
    void deposit_increasesBalance() {
        savings.deposit(new BigDecimal("500.00"));
        assertEquals(new BigDecimal("1500.00"), savings.getBalance());
    }

    @Test
    void withdraw_decreasesBalance() {
        savings.withdraw(new BigDecimal("400.00"));
        assertEquals(new BigDecimal("600.00"), savings.getBalance());
    }

    @Test
    void savings_throwsWhenBelowMinimumBalance() {
        assertThrows(InsufficientFundsException.class, () ->
            savings.withdraw(new BigDecimal("600.00"))
        );
    }

    @Test
    void current_allowsOverdraft() {
        current.withdraw(new BigDecimal("5000.00"));
        assertEquals(new BigDecimal("-4000.00"), current.getBalance());
    }

    @Test
    void current_throwsWhenExceedsOverdraftLimit() {
        assertThrows(InsufficientFundsException.class, () ->
            current.withdraw(new BigDecimal("15000.00"))
        );
    }

    @Test
    void fd_throwsOnPrematureWithdrawal() {
        assertThrows(PrematureWithdrawalException.class, () ->
            fd.withdraw(new BigDecimal("1000.00"))
        );
    }

    @Test
    void savings_calculateInterest_isCorrect() {
        BigDecimal interest = savings.calculateInterest();
        assertEquals(new BigDecimal("40.00"), interest);
    }

    @Test
    void deposit_throwsOnNegativeAmount() {
        assertThrows(InvalidAmountException.class, () ->
            savings.deposit(new BigDecimal("-100.00"))
        );
    }

    @Test
    void bank_throwsWhenAccountNotFound() {
        Bank bank = new Bank("Test Bank");
        assertThrows(AccountNotFoundException.class, () ->
            bank.getAccount("INVALID")
        );
    }

    @Test
    void transaction_historyRecordsDeposit() {
        savings.deposit(new BigDecimal("500.00"));
        assertEquals(1, savings.getTransactions().size());
        assertEquals(com.banking.model.Transaction.Type.DEPOSIT,
                savings.getTransactions().get(0).getType());
    }
}