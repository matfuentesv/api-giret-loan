package com.giret.loan.controller;

import com.giret.loan.model.Prestamo;
import com.giret.loan.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class LoanController {

    @Autowired
    LoanService loanService;


    @PostMapping("/saveLoan")
    public ResponseEntity<Prestamo> saveLoan(@RequestBody Prestamo prestamo) {
        Prestamo saved = loanService.saveLoan(prestamo);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/findAllLoan")
    public ResponseEntity<List<Prestamo>> getAllLoan() {
        return ResponseEntity.ok(loanService.getAllLoans());
    }

    @GetMapping("/findLoandByResource/{id}")
    public ResponseEntity<List<Prestamo>> findLoandByResource(@PathVariable Long id) {
        return ResponseEntity.ok(loanService.getLoansByResourceId(id));
    }

    @PutMapping("/updateLoanByState/{state}/{id}")
    public ResponseEntity<Prestamo> updateLoanByState(@PathVariable ("state")String state,@PathVariable ("id")Long id) {
        return ResponseEntity.ok(loanService.updateStateById(id,state));
    }

}
