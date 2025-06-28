package com.giret.apigiretresources.service;

import com.giret.apigiretresources.model.Loan;
import com.giret.apigiretresources.repository.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoanServiceImpl implements LoanService {

    @Autowired
    LoanRepository loanRepository;


    @Override
    public Loan saveLoan(Loan loan) {
        return loanRepository.save(loan);
    }

    @Override
    public List<Loan> getAllLoans() {
        return loanRepository.findAll();
    }

    @Override
    public List<Loan> getLoansByResourceId(Long resourceId) {
        return loanRepository.findAll().stream()
                .filter(p -> p.getRecursoId().equals(resourceId))
                .toList();
    }

    @Override
    public Loan updateLoan(Long id, Loan loan) {
        Loan existing = loanRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Prestamo no encontrado"));

        existing.setRecursoId(loan.getRecursoId());
        existing.setFechaPrestamo(loan.getFechaPrestamo());
        existing.setFechaDevolucion(loan.getFechaDevolucion());
        existing.setSolicitante(loan.getSolicitante());
        existing.setEstado(loan.getEstado());

        return loanRepository.save(existing);
    }

    @Override
    public void deleteLoan(Long id) {
        loanRepository.deleteById(id);
    }
}
