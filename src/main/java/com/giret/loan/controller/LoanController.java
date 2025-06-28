package com.giret.loan.controller;

import com.giret.loan.model.Loan;
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
    public ResponseEntity<Loan> saveLoan(@RequestBody Loan loan) {
        Loan saved = loanService.saveLoan(loan);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/findAllLoan")
    public ResponseEntity<List<Loan>> getAllLoan() {
        return ResponseEntity.ok(loanService.getAllLoans());
    }

    @GetMapping("/findLoandByResource/{id}")
    public ResponseEntity<List<Loan>> findLoandByResource(@PathVariable Long id) {
        return ResponseEntity.ok(loanService.getLoansByResourceId(id));
    }

}
