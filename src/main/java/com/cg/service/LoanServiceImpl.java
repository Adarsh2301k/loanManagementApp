package com.cg.service;

import com.cg.entity.Loan;
import com.cg.exceptions.DuplicateLoanApplicationException;
import com.cg.exceptions.InvalidLoanAmountException;
import com.cg.exceptions.LoanNotFoundException;
import com.cg.repository.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoanServiceImpl implements LoanService {

    @Autowired
    private LoanRepository repository;

    @Override
    public Loan createLoan(Loan loan) {

        // Rule 1 & 2
        if (loan.getLoanAmount() <= 0 || loan.getLoanAmount() > 5000000) {
            throw new InvalidLoanAmountException("Loan amount must be between 1 and 5000000");
        }

        // Rule 3
        repository.findByApplicantNameAndStatus(loan.getApplicantName(), "PENDING")
                .ifPresent(l -> {
                    throw new DuplicateLoanApplicationException("User already has a pending loan");
                });

        loan.setStatus("PENDING");
        return repository.save(loan);
    }

    @Override
    public List<Loan> getAllLoans() {
        return repository.findAll();
    }

    @Override
    public Loan getLoanById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new LoanNotFoundException("Loan not found with id: " + id));
    }

    @Override
    public Loan updateLoanStatus(Long id, String status) {
        Loan loan = getLoanById(id);

        if (!status.equalsIgnoreCase("APPROVED") && !status.equalsIgnoreCase("REJECTED")) {
            throw new IllegalArgumentException("Status must be APPROVED or REJECTED");
        }

        loan.setStatus(status.toUpperCase());
        return repository.save(loan);
    }
}