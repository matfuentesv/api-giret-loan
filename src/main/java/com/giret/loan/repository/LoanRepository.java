package com.giret.loan.repository;


import com.giret.loan.model.Prestamo;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LoanRepository extends JpaRepository<Prestamo, Long> {

    @Transactional
    @Query("UPDATE Prestamo l SET l.estado = :estado WHERE l.idPrestamo = :id")
    Prestamo updateLoanById(@Param("id") Long id, @Param("estado") String estado);


}
