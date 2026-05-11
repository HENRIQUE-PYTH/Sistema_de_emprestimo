package loan.system.com.Loan.domain;

import jakarta.persistence.*;
import loan.system.com.Book.domain.Book;
import loan.system.com.Loan.LoanStatus;
import loan.system.com.User.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "loan")
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne()
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    @Column(name = "loan_date")
    private LocalDate loanDate;

    @Column(name = "due_date")
    private LocalDate dueDate;

    @Column(name = "return_date")
    private LocalDate returnDate;

    @Column(name = "fine_amount", nullable = false)
    private BigDecimal fineAmount;

    @Column(name = "days_late")
    private BigDecimal daysLate;

    @Enumerated(EnumType.STRING)
    private LoanStatus status;

    public Loan(User user,
                Book book,
                LocalDate loanDate,
                LocalDate dueDate,
                LocalDate returnDate,
                BigDecimal fineAmount,
                BigDecimal daysLate,
                LoanStatus status) {
        this.user = user;
        this.book = book;
        this.loanDate = loanDate;
        this.dueDate = dueDate;
        this.returnDate = returnDate;
        this.fineAmount = fineAmount;
        this.daysLate = daysLate;
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Loan that)) return false;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }
}
