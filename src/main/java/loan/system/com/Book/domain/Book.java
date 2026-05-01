package loan.system.com.Book.domain;

import jakarta.persistence.*;
import loan.system.com.Book.BookStatus;
import loan.system.com.Book.serializer.GenderSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import loan.system.com.Loan.domain.Loan;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Objects;
@NoArgsConstructor
@Getter
@Setter
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

    @Enumerated(EnumType.STRING)
    private BookStatus status;

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

    public Book(String title, String author, BookStatus status, String isbn, String genre, Integer publicationYear, Boolean active) {
        this.title = title;
        this.author = author;
        this.status = status;
        this.isbn = isbn;
        this.genre = genre;
        this.publicationYear = publicationYear;
        this.active = active;
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
