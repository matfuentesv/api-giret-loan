package com.giret.loan.service;

import com.giret.loan.model.Prestamo;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface LoanService {

    Prestamo saveLoan(Prestamo prestamo);
    List<Prestamo> getAllLoans();
    List<Prestamo> getLoansByResourceId(Long resourceId);
    Prestamo updateLoan(Long id, Prestamo prestamo);
    void deleteLoan(Long id);
    Prestamo updateStateById(Long id,String estado);
}
