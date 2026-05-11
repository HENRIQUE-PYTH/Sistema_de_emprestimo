package loan.system.com.Loan.LoanMapper;

import loan.system.com.Loan.domain.Loan;
import loan.system.com.Loan.dto.LoanRequestDTO;
import loan.system.com.Loan.dto.LoanResponseDTO;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class LoanMapper {

    public Loan toEntity (LoanRequestDTO dto){
        Loan loan = new Loan();
        loan.setLoanDate(dto.getLoanDate());
        loan.setDueDate(dto.getDueDate());
        return loan;
    }

    public LoanResponseDTO toResponse (Loan loan){
        LoanResponseDTO dto = new LoanResponseDTO();
        dto.setId(loan.getId());
        dto.setUserId(loan.getUser().getId());
        dto.setBookId(loan.getBook().getId());
        dto.setLoanDate(loan.getLoanDate());
        dto.setDueDate(loan.getDueDate());
        dto.setReturnDate(loan.getReturnDate());
        dto.setFineAmount(loan.getFineAmount());
        dto.setDaysLate(loan.getDaysLate());
        return dto;
    }
}
