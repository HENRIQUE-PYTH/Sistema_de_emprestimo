package loan.system.com.Loan.domain;

import jakarta.persistence.*;
import loan.system.com.Book.domain.Book;
import loan.system.com.User.domain.User;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "Loans")
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
    private Integer daysLate;

    public Loan() {
    }

    public Loan(User user,
                Book book,
                LocalDate loanDate,
                LocalDate dueDate,
                LocalDate returnDate,
                BigDecimal fineAmount,
                Integer daysLate) {
        this.user = user;
        this.book = book;
        this.loanDate = loanDate;
        this.dueDate = dueDate;
        this.returnDate = returnDate;
        this.fineAmount = fineAmount;
        this.daysLate = daysLate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public LocalDate getLoanDate() {
        return loanDate;
    }

    public void setLoanDate(LocalDate loanDate) {
        this.loanDate = loanDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    public BigDecimal getFineAmount() {
        return fineAmount;
    }

    public void setFineAmount(BigDecimal fineAmount) {
        this.fineAmount = fineAmount;
    }

    public Integer getDaysLate() {
        return daysLate;
    }

    public void setDaysLate(Integer daysLate) {
        this.daysLate = daysLate;
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
