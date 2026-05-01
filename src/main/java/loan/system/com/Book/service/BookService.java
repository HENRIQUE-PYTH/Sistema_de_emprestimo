package loan.system.com.Book.service;

import loan.system.com.Book.BookStatus;
import loan.system.com.Book.domain.Book;
import loan.system.com.Book.repository.BookRepository;
import loan.system.com.exception.BadRequestException;
import loan.system.com.exception.NotFoundException;
import org.springframework.stereotype.Service;


@Service
public class BookService {

    private final BookRepository repository;

    public BookService(BookRepository repository) {
        this.repository = repository;
    }



    public Book findById (Long id){
        Book book = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Book not found"));
        return repository.save(book);
    }

    public Book createBook (Book book){
        if (book.getTitle().isBlank()){
            throw new BadRequestException("It is not possible to create a book without a name.");
        }
        if (book.getAuthor().isBlank()){
            throw new BadRequestException("An author is needed to create a book.");
        }
        if (book.getStatus() == BookStatus.INACTIVE || book.getStatus() == null){
            throw new BadRequestException("The book needs an evaluation so that it can be recommended or not.");
        }
        if (book.getIsbn().isBlank()) {
            throw new BadRequestException("The book needs a verification code (ISBN).");
        }
        if (book.getGenre().isBlank()){
            throw new BadRequestException("The book needs a genre.");
        }
        if (book.getPublicationYear() == null){
            throw new BadRequestException("The publication date of the book must be provided.");
        }

        Book save = new Book();

        save.setTitle(book.getTitle());
        save.setAuthor(book.getAuthor());
        save.setStatus(BookStatus.AVAILABLE);
        save.setIsbn(book.getIsbn());
        save.setGenre(book.getGenre());
        save.setPublicationYear(book.getPublicationYear());
        save.setActive(true);

        return repository.save(save);
    }

    public void deleteBook (Long id){
        Book book = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Book not found"));
        book.setActive(false);
    }
}
