package com.banking.exception;

import java.time.LocalDate;

public class PrematureWithdrawalException extends BankingException {

    private final LocalDate maturityDate;

    public PrematureWithdrawalException(LocalDate maturityDate) {
        super("Fixed deposit is locked until " + maturityDate);
        this.maturityDate = maturityDate;
    }

    public LocalDate getMaturityDate() { return maturityDate; }
}