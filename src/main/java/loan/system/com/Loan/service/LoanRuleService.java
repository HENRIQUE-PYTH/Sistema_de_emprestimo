package loan.system.com.Loan.service;

import loan.system.com.Loan.LoanStatus;
import loan.system.com.Loan.domain.Loan;
import loan.system.com.Loan.repository.LoanRepository;
import loan.system.com.User.domain.User;
import loan.system.com.User.repository.UserRepository;
import loan.system.com.exception.NotFoundException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class LoanRuleService {

    private final LoanRepository repository;
    private final UserRepository userRepository;

    public LoanRuleService(LoanRepository repository, UserRepository userRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
    }

    public List<Loan> getAllActiveLoans (){
        return repository.findByLoansActive(LoanStatus.ACTIVE);
    }

    public List<Loan> getActiveLoansByUser(Long userId){
        return repository.findByUserIdAndStatus(userId, LoanStatus.ACTIVE);
    }

    public Boolean hasReachedLoanLimit (Long userId){
        int activeLoans = repository.countByUserIdAndStatus(userId, LoanStatus.ACTIVE);
        int limit = 3;
        return activeLoans >= limit;
    }

    public BigDecimal getTotalPendingFines (Long userId){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found"));
        BigDecimal total = BigDecimal.ZERO;

        for(Loan loan : user.getLoans()){
            if (loan.getFineAmount() != null){
                total = total.add(loan.getFineAmount());
            }
        }

        return total;
    }

    public Boolean hasPendingFines(Long userId){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found"));
        for (Loan loan : user.getLoans()){
            if (loan.getStatus() == LoanStatus.OVERDUE){
                return true;
            }
        }

        return false;
    }

    public Boolean hasOverdueLoans (Long userId){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found"));
        for (Loan loan : user.getLoans()){
            if (loan.getStatus() == LoanStatus.OVERDUE){
                return true;
            }
        }
        return false;
    }

}
