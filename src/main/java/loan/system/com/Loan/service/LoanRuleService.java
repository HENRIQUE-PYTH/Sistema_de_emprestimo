package loan.system.com.Loan.service;

import loan.system.com.Loan.LoanStatus;
import loan.system.com.Loan.domain.Loan;
import loan.system.com.Loan.repository.LoanRepository;
import loan.system.com.User.domain.User;
import loan.system.com.User.repository.UserRepository;
import loan.system.com.exception.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LoanRuleService {

    private final LoanRepository repository;
    private final UserRepository userRepository;

    public LoanRuleService(LoanRepository repository, UserRepository userRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
    }

    public Boolean hasReachedLoanLimit (Long userId){
        int activeLoans = repository.countByUserIdAndStatus(userId, LoanStatus.ACTIVE);
        int limit = 3;
        return activeLoans >= limit;
    }

    @Transactional
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

    @Transactional
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
