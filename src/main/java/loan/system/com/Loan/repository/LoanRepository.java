package loan.system.com.Loan.repository;

import loan.system.com.Loan.LoanStatus;
import loan.system.com.Loan.domain.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {
    List<Loan> findByUserId(Long id);
    List<Loan> findByDueDateBeforeAndStatus (LocalDate date, LoanStatus status);
}
