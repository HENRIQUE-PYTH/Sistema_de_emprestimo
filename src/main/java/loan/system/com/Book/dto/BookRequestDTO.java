package loan.system.com.Book.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class BookRequestDTO {

    @NotBlank(message = "Author is required")
    private String author;

    @NotBlank(message = "Title is required")
    private String title;

    @NotNull(message = "Available is required")
    private Boolean available;

    @NotBlank(message = "ISBN is required")
    private String isbn;

    @NotBlank(message = "Genre is required")
    private String genre;

    @NotNull(message = "Publication year is required")
    private Integer publicationYear;

    public BookRequestDTO() {
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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
}
