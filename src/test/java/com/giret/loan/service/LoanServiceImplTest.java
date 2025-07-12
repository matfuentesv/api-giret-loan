package com.giret.loan.service;

import com.giret.loan.model.Prestamo;
import com.giret.loan.repository.LoanRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LoanServiceImplTest {

    @InjectMocks
    private LoanServiceImpl loanService;

    @Mock
    private LoanRepository loanRepository;

    private Prestamo prestamo;

    @BeforeEach
    void setUp() {
        prestamo = new Prestamo();
        prestamo.setIdPrestamo(1L);
        prestamo.setRecursoId(10L);
        prestamo.setFechaPrestamo("2024-07-15");
        prestamo.setFechaDevolucion("2024-07-30");
        prestamo.setSolicitante("Juan Perez");
        prestamo.setEstado("Activo");
    }

    @Test
    void shouldSaveLoan() {
        when(loanRepository.save(any(Prestamo.class))).thenReturn(prestamo);

        Prestamo saved = loanService.saveLoan(prestamo);

        assertNotNull(saved);
        assertEquals(1L, saved.getIdPrestamo());
        verify(loanRepository, times(1)).save(prestamo);
    }

    @Test
    void shouldGetAllLoans() {
        when(loanRepository.findAll()).thenReturn(List.of(prestamo));

        List<Prestamo> result = loanService.getAllLoans();

        assertEquals(1, result.size());
        assertEquals("Juan Perez", result.get(0).getSolicitante());
    }

    @Test
    void shouldGetLoansByResourceId() {
        when(loanRepository.findAll()).thenReturn(List.of(prestamo));

        List<Prestamo> result = loanService.getLoansByResourceId(10L);

        assertEquals(1, result.size());
        assertEquals(10L, result.get(0).getRecursoId());
    }

    @Test
    void shouldUpdateLoan() {
        when(loanRepository.findById(1L)).thenReturn(Optional.of(prestamo));
        when(loanRepository.save(any(Prestamo.class))).thenReturn(prestamo);

        Prestamo update = new Prestamo();
        update.setRecursoId(20L);
        update.setFechaPrestamo("2024-08-01");
        update.setFechaDevolucion("2024-08-15");
        update.setSolicitante("Maria Lopez");
        update.setEstado("Devuelto");

        Prestamo updated = loanService.updateLoan(1L, update);

        assertNotNull(updated);
        assertEquals("Devuelto", updated.getEstado());
        verify(loanRepository, times(1)).save(any(Prestamo.class));
    }

    @Test
    void shouldDeleteLoan() {
        doNothing().when(loanRepository).deleteById(1L);

        loanService.deleteLoan(1L);

        verify(loanRepository, times(1)).deleteById(1L);
    }

    @Test
    void shouldUpdateStateById_Success() {
        when(loanRepository.updateLoanById(1L, "Devuelto")).thenReturn(1);
        when(loanRepository.findById(1L)).thenReturn(Optional.of(prestamo));

        Prestamo updated = loanService.updateStateById(1L, "Devuelto");

        assertNotNull(updated);
        verify(loanRepository, times(1)).updateLoanById(1L, "Devuelto");
    }

    @Test
    void shouldUpdateStateById_NotFound() {
        when(loanRepository.updateLoanById(1L, "Devuelto")).thenReturn(1);
        when(loanRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            loanService.updateStateById(1L, "Devuelto");
        });
    }

    @Test
    void shouldUpdateStateById_NoRowsUpdated() {
        when(loanRepository.updateLoanById(1L, "Devuelto")).thenReturn(0);

        assertThrows(RuntimeException.class, () -> {
            loanService.updateStateById(1L, "Devuelto");
        });
    }

    @Test
    void shouldThrowWhenUpdateLoanNotFound() {
        when(loanRepository.findById(1L)).thenReturn(Optional.empty());

        Prestamo update = new Prestamo();
        update.setRecursoId(20L);
        update.setFechaPrestamo("2024-08-01");
        update.setFechaDevolucion("2024-08-15");
        update.setSolicitante("Maria Lopez");
        update.setEstado("Devuelto");

        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            loanService.updateLoan(1L, update);
        });

        assertEquals("Prestamo no encontrado", ex.getMessage());
    }



}
