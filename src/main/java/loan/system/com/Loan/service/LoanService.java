package loan.system.com.Loan.service;

import loan.system.com.Loan.domain.Loan;
import loan.system.com.Loan.repository.LoanRepository;
import loan.system.com.exception.BadRequestException;
import loan.system.com.exception.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class LoanService {

    private final LoanRepository repository;

    public LoanService(LoanRepository repository) {
        this.repository = repository;
    }

    public Loan create (Loan loan){
        if (loan.getUser().getName().isBlank()){
            throw new BadRequestException("The field {name} is null, and needs to be filled in.");
        }
        return null;
    }

    public Loan findById(Long id){
        Loan loan = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Loan not found"));
        return repository.save(loan);
    }

    public List<Loan> listOfLoan(){
        return repository.findAll();
    }
}
