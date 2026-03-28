package com.cg.controller;

import com.cg.entity.Loan;
import com.cg.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/loans")
public class LoanController {

    @Autowired
    private LoanService service;

    @PostMapping
    public Loan createLoan(@RequestBody Loan loan) {
        return service.createLoan(loan);
    }

    @GetMapping
    public List<Loan> getAllLoans() {
        return service.getAllLoans();
    }

    @GetMapping("/{id}")
    public Loan getLoanById(@PathVariable Long id) {
        return service.getLoanById(id);
    }

    @PutMapping("/{id}/status")
    public Loan updateStatus(@PathVariable Long id, @RequestBody Map<String, String> request) {
        return service.updateLoanStatus(id, request.get("status"));
    }
}