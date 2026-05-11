package loan.system.com.Book.BookMapper;

import loan.system.com.Book.domain.Book;
import loan.system.com.Book.dto.BookRequestDTO;
import loan.system.com.Book.dto.BookResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class MapperBook {

    public Book toEntity(BookRequestDTO dto){
        Book book = new Book();
        book.setTitle(dto.getTitle());
        book.setAuthor(dto.getAuthor());
        book.setIsbn(dto.getIsbn());
        book.setGenre(dto.getGenre());
        book.setPublicationYear(dto.getPublicationYear());
        return book;
    }

    public BookResponseDTO toResponse (Book book){
        BookResponseDTO dto = new BookResponseDTO();
        dto.setId(book.getId());
        dto.setTitle(book.getTitle());
        dto.setAuthor(book.getAuthor());
        dto.setIsbn(book.getIsbn());
        dto.setGenre(book.getGenre());
        dto.setPublicationYear(book.getPublicationYear());
        dto.setActive(book.getActive());
        return dto;
    }
}
