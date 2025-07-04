package com.giret.loan.service;

import com.giret.loan.model.Prestamo;
import com.giret.loan.repository.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class LoanServiceImpl implements LoanService {

    @Autowired
    LoanRepository loanRepository;


    @Override
    public Prestamo saveLoan(Prestamo prestamo) {
        return loanRepository.save(prestamo);
    }

    @Override
    public List<Prestamo> getAllLoans() {
        return loanRepository.findAll();
    }

    @Override
    public List<Prestamo> getLoansByResourceId(Long resourceId) {
        return loanRepository.findAll().stream()
                .filter(p -> p.getRecursoId().equals(resourceId))
                .toList();
    }

    @Override
    public Prestamo updateLoan(Long id, Prestamo prestamo) {
        Prestamo existing = loanRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Prestamo no encontrado"));

        existing.setRecursoId(prestamo.getRecursoId());
        existing.setFechaPrestamo(prestamo.getFechaPrestamo());
        existing.setFechaDevolucion(prestamo.getFechaDevolucion());
        existing.setSolicitante(prestamo.getSolicitante());
        existing.setEstado(prestamo.getEstado());

        return loanRepository.save(existing);
    }

    @Override
    public void deleteLoan(Long id) {
        loanRepository.deleteById(id);
    }

    @Override
    public Prestamo updateStateById(Long id, String estado) {
        int updated = loanRepository.updateLoanById(id, estado);
        if (updated == 1) {
            return loanRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Préstamo no encontrado"));
        } else {
            throw new RuntimeException("No se actualizó ningún préstamo");
        }
    }
}
