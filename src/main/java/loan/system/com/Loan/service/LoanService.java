package loan.system.com.Loan.service;

import loan.system.com.Book.BookStatus;
import loan.system.com.Book.domain.Book;
import loan.system.com.Book.repository.BookRepository;
import loan.system.com.Loan.LoanStatus;
import loan.system.com.Loan.domain.Loan;
import loan.system.com.Loan.repository.LoanRepository;
import loan.system.com.User.UserStatus;
import loan.system.com.User.domain.User;
import loan.system.com.User.repository.UserRepository;
import loan.system.com.exception.ConflictRequestException;
import loan.system.com.exception.NotFoundException;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;


@Service
public class LoanService {

    private final LoanRepository repository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final LoanRuleService service;

    public LoanService(LoanRepository repository, BookRepository bookRepository, UserRepository userRepository, LoanRuleService service) {
        this.repository = repository;
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
        this.service = service;
    }

    public List<Loan> findAllLoans (){
        return repository.findAll();
    }

    public List<Loan> getAllActiveLoans (){
        return repository.findByStatus(LoanStatus.ACTIVE);
    }

    public List<Loan> getActiveLoansByUser(Long userId){
        return repository.findByUserIdAndStatus(userId, LoanStatus.ACTIVE);
    }

    public Loan findById(Long loanId){
        Loan loan = repository.findById(loanId)
                .orElseThrow(() -> new NotFoundException("Loan not found"));
        return repository.save(loan);
    }

    public List <Loan> findByUser (Long id){
        return repository.findByUserId(id);
    }

    public List<Loan> getUserLoanHistory (Long userId){
        userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found"));

        return repository.findByUserIdOrderByLoanDateDesc(userId);
    }

    public List<User> getUsersWithOverdueLoans() {

        List<Loan> overdueLoans = repository
                .findByDueDateBeforeAndStatus(LocalDate.now(), LoanStatus.ACTIVE);

        return overdueLoans.stream()
                .map(Loan::getUser)
                .distinct()
                .toList();
    }

    public List<Loan> findOverdueLoans() {
        LocalDate today = LocalDate.now();

        List<Loan> overdueLoans = repository
                .findByDueDateBeforeAndStatus(today, LoanStatus.ACTIVE);

        for (Loan loan : overdueLoans) {
            long daysLate = ChronoUnit.DAYS.between(loan.getDueDate(), today);

            if (daysLate > 0) {
                loan.setDaysLate((BigDecimal.valueOf(daysLate)));

                BigDecimal finePerDay = new BigDecimal("2.50");
                BigDecimal fineAmount = finePerDay.multiply(BigDecimal.valueOf(daysLate));

                loan.setFineAmount(fineAmount);
            }
        }
        return overdueLoans;
    }

    public Loan createLoan (Long userId, Long bookId){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found"));

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new NotFoundException("Book not found"));

        if (user.getStatus() == UserStatus.INACTIVE || user.getStatus() == UserStatus.BLOCKED){
            throw new ConflictRequestException("This user is blocked or inactive");
        }
        if (book.getStatus() != BookStatus.LOANED){
            throw new ConflictRequestException("This book is already borrowed");
        }
        if (!book.getActive()){
            throw new ConflictRequestException("This is book is not available");
        }
        if (service.hasPendingFines(userId)){
            throw new ConflictRequestException("The user has pending fines");
        }
        if (service.hasReachedLoanLimit(userId)){
            throw new ConflictRequestException("User has reached loan limit");
        }
        if (service.hasOverdueLoans(userId)){
            user.setStatus(UserStatus.BLOCKED);
            throw new ConflictRequestException("The user is blocked");
        }
        Loan loan = new Loan();

        LocalDate loanDate = LocalDate.now();
        LocalDate dueDate = loanDate.plusDays(7);

        long daysLate = 0;

        LocalDate baseDate = (loan.getReturnDate() != null)
                ? loan.getReturnDate()
                : LocalDate.now();

        if (baseDate.isAfter(loan.getDueDate())) {
            daysLate = ChronoUnit.DAYS.between(loan.getDueDate(), baseDate);
        }

        BigDecimal finePerDay = new BigDecimal("2.50");

        BigDecimal fineAmount = finePerDay.multiply(BigDecimal.valueOf(daysLate));

        Loan loanSave = new Loan(
                user,
                book,
                loanDate,
                dueDate,
                baseDate,
                fineAmount,
                finePerDay,
                LoanStatus.ACTIVE
        );
        userRepository.save(user);
        return repository.save(loanSave);
    }

    public Loan returnLoan(Long loanId) {

        Loan loan = repository.findById(loanId)
                .orElseThrow(() -> new NotFoundException("Loan not found"));

        if (loan.getStatus() == LoanStatus.RETURNED) {
            throw new ConflictRequestException("Loan already returned");
        }

        LocalDate returnDate = LocalDate.now();
        loan.setReturnDate(returnDate);

        loan.setStatus(LoanStatus.RETURNED);

        long daysLate = 0;

        if (returnDate.isAfter(loan.getDueDate())) {
            daysLate = ChronoUnit.DAYS.between(loan.getDueDate(), returnDate);
        }

        loan.setDaysLate(BigDecimal.valueOf(daysLate));

        BigDecimal fineAmount = BigDecimal.ZERO;

        if (daysLate > 0) {
            BigDecimal finePerDay = new BigDecimal("2.50");
            fineAmount = finePerDay.multiply(BigDecimal.valueOf(daysLate));
        }

        loan.setFineAmount(fineAmount);

        Book book = loan.getBook();
        book.setStatus(BookStatus.AVAILABLE);

        bookRepository.save(book);

        return repository.save(loan);
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


}