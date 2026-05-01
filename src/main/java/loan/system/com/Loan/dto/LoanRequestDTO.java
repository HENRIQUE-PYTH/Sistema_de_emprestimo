package loan.system.com.Loan.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class LoanRequestDTO {

    @NotNull(message = "User ID is required.")
    private Long userId;

    @NotNull(message = "The book ID is required.")
    private Long bookId;

    @NotNull(message = "Company data is required")
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate loanDate;

    @NotNull(message = "A due date is required.")
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dueDate;

}
