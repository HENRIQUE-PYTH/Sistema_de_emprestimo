package loan.system.com.Loan.dto;



import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class LoanResponseDTO {

    private Long id;

    private Long userId;
    private Long bookId;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate loanDate;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dueDate;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate returnDate;

    private BigDecimal fineAmount;
    private Integer daysLate;

}
