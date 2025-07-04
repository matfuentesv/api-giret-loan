package com.giret.loan.repository;


import com.giret.loan.model.Prestamo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;


public interface LoanRepository extends JpaRepository<Prestamo, Long> {

    @Transactional
    @Modifying
    @Query("UPDATE Prestamo l SET l.estado = :estado WHERE l.idPrestamo = :id")
    int updateLoanById(@Param("id") Long id, @Param("estado") String estado);


}
