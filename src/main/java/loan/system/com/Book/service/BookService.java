package loan.system.com.Book.service;

import loan.system.com.Book.domain.Book;
import loan.system.com.Book.dto.BookRequestDTO;
import loan.system.com.Book.repository.BookRepository;
import loan.system.com.exception.BadRequestException;
import loan.system.com.exception.GlobalExceptionHandler;
import loan.system.com.exception.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    private final BookRepository repository;
    private GlobalExceptionHandler exceptionHandler;

    public BookService(BookRepository repository) {
        this.repository = repository;
    }



    public Book findById (Long id){
        Book book = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Book not found"));
        return repository.save(book);
    }

    public Book createBook (Book book){
        if (book.getTitle().isBlank() || book.getTitle() == null){
            throw new BadRequestException("It is not possible to create a book without a name.");
        }
        if (book.getAuthor().isBlank() || book.getAuthor() == null){
            throw new BadRequestException("An author is needed to create a book.");
        }
        if (book.getAvailable() == false || book.getAvailable() == null){
            throw new BadRequestException("The book needs an evaluation so that it can be recommended or not.");
        }
        if (book.getIsbn().isBlank() || book.getIsbn() == null) {
            throw new BadRequestException("The book needs a verification code (ISBN).");
        }
        if (book.getGenre().isBlank() || book.getGenre() == null){
            throw new BadRequestException("The book needs a genre.");
        }
        if (book.getPublicationYear() == null){
            throw new BadRequestException("The publication date of the book must be provided.");
        }

        Book save = new Book();

        save.setTitle(book.getTitle());
        save.setAuthor(book.getAuthor());
        save.setAvailable(book.getAvailable());
        save.setIsbn(book.getIsbn());
        save.setGenre(book.getGenre());
        save.setPublicationYear(book.getPublicationYear());
        save.setActive(book.getActive());

        return repository.save(save);
    }

    public void deleteBook (Long id){
        Book book = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Book not found"));
        repository.delete(book);
    }
}
