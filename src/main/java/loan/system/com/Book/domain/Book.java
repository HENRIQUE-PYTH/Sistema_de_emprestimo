package loan.system.com.Book.domain;

import jakarta.persistence.*;
import loan.system.com.Book.serializer.GenderSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import loan.system.com.Loan.domain.Loan;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "Books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "Title", length = 200)
    private String title;

    @Column(nullable = false, name = "Author", length = 100)
    private String author;

    @Column(name = "Available", nullable = false)
    private Boolean available;

    @Column(name = "book_number", nullable = false, unique = true)
    private String isbn;

    @Column(name = "gênero", nullable = false)
    @JsonSerialize(using = GenderSerializer.class)
    private String genre;

    @Column(name = "date_of_publication", nullable = false)
    private Integer publicationYear;

    @Column(name = "active")
    private Boolean active;

    @OneToMany(mappedBy = "book")
    private List<Loan> loan;

    public Book() {
    }

    public Book(String title, String author, Boolean available, String isbn, String genre, Integer publicationYear, Boolean active) {
        this.title = title;
        this.author = author;
        this.available = available;
        this.isbn = isbn;
        this.genre = genre;
        this.publicationYear = publicationYear;
        this.active = active;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public Integer getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(Integer publicationYear) {
        this.publicationYear = publicationYear;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public List<Loan> getLoan() {
        return loan;
    }

    public void setLoan(List<Loan> loan) {
        this.loan = loan;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Book book)) return false;
        return Objects.equals(getId(), book.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }
}
