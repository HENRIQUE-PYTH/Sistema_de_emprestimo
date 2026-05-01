package loan.system.com.Book.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BookResponseDTO {

    private Long id;
    private String title;
    private String author;
    private Boolean available;
    private String isbn;
    private String genre;
    private Integer publicationYear;
    private Boolean active;

}
