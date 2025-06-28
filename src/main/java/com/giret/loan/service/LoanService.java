package com.giret.loan.service;

import com.giret.loan.model.Loan;

import java.util.List;


public interface LoanService {

    Loan saveLoan(Loan loan);
    List<Loan> getAllLoans();
    List<Loan> getLoansByResourceId(Long resourceId);
    Loan updateLoan(Long id, Loan loan);
    void deleteLoan(Long id);
}
