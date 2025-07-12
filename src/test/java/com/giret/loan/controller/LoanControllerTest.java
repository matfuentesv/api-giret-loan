package com.giret.loan.controller;


import com.giret.loan.model.Prestamo;
import com.giret.loan.service.LoanService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(LoanController.class)
class LoanControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private LoanService loanService;

    @TestConfiguration
    static class TestConfig {
        @Bean
        public LoanService loanService() {
            return Mockito.mock(LoanService.class);
        }
    }

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
    void shouldGetAllLoans() throws Exception {
        when(loanService.getAllLoans()).thenReturn(List.of(prestamo));

        mockMvc.perform(get("/api/findAllLoan"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idPrestamo").value(1L))
                .andExpect(jsonPath("$[0].recursoId").value(10))
                .andExpect(jsonPath("$[0].fechaPrestamo").value("2024-07-15"))
                .andExpect(jsonPath("$[0].fechaDevolucion").value("2024-07-30"))
                .andExpect(jsonPath("$[0].solicitante").value("Juan Perez"))
                .andExpect(jsonPath("$[0].estado").value("Activo"));
    }


    @Test
    void shouldCreateLoan() throws Exception {
        when(loanService.saveLoan(any(Prestamo.class))).thenReturn(prestamo);

        String json = """
            {
                "idPrestamo":1,
                "recursoId": 10,
                "fechaPrestamo": "2024-07-15",
                "fechaDevolucion": "2024-07-30",
                "solicitante": "Juan Perez",
                "estado": "Activo"
            }
        """;

        mockMvc.perform(post("/api/saveLoan")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.recursoId").value(10))
                .andExpect(jsonPath("$.solicitante").value("Juan Perez"))
                .andExpect(jsonPath("$.estado").value("Activo"));
    }

    @Test
    void shouldGetLoanById() throws Exception {
        when(loanService.getLoansByResourceId(1L)).thenReturn(List.of(prestamo));

        mockMvc.perform(get("/api/findLoandByResource/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idPrestamo").value(1L))
                .andExpect(jsonPath("$[0].recursoId").value(10))
                .andExpect(jsonPath("$[0].fechaPrestamo").value("2024-07-15"))
                .andExpect(jsonPath("$[0].solicitante").value("Juan Perez"))
                .andExpect(jsonPath("$[0].estado").value("Activo"));
    }

    @Test
    void shouldUpdateLoanByState() throws Exception {
        when(loanService.updateStateById(1L, "Devuelto")).thenReturn(prestamo);

        mockMvc.perform(put("/api/updateLoanByState/Devuelto/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.estado").value("Activo"));


    }

}
