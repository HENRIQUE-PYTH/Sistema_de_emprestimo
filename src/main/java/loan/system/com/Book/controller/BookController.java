package loan.system.com.Book.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;
import loan.system.com.Book.BookMapper.MapperBook;
import loan.system.com.Book.domain.Book;
import loan.system.com.Book.dto.BookRequestDTO;
import loan.system.com.Book.dto.BookResponseDTO;
import loan.system.com.Book.service.BookService;
import loan.system.com.exception.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "Books", description = "endpoint for managing Books")
@RequestMapping("/books")
public class BookController {

    private final BookService service;
    private final MapperBook mapper;

    public BookController(BookService service, MapperBook mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Searches for the book by ID.",
            description = "Search the database for the book specified by the ID."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Book found sucessfully",
            content = @Content(schema = @Schema(implementation = BookResponseDTO.class))
    )
    @ApiResponse(
            responseCode = "404",
            description = "Book not found, the ID might be wrong.",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))
    )
    public BookResponseDTO findById (@PathVariable Long id){
        Book book = service.findById(id);
        return mapper.toResponse(book);
    }

    @PostMapping
    @Operation(
            summary = "Create one new book linked to a user",
            description = "Create a new book with information about the book, ranging from the book's title to its publication date."
    )
    @ApiResponse(
            responseCode = "201",
            description = "Book has been created successfully",
            content = @Content(schema = @Schema(implementation = BookResponseDTO.class))
    )
    @ApiResponse(
            responseCode = "400",
            description = "Inválid data for create the book.",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))
    )
    @RequestBody(
            description = "Book data for creating",
            required = true,
            content = @Content(schema = @Schema(implementation = BookRequestDTO.class))
    )
    public BookResponseDTO createBook (@org.springframework.web.bind.annotation.RequestBody @Valid BookRequestDTO dto){
        Book book = mapper.toEntity(dto);
        Book save = service.createBook(book);
        return mapper.toResponse(save);
    }

    @DeleteMapping("/{id}/delete")
    @Operation(
            summary = "Delete the book by ID.",
            description = "Search for a book by its ID and permanently delete it."
    )
    @ApiResponse(
            responseCode = "204",
            description = "Book excluded on sucessfully"
    )
    @ApiResponse(
            responseCode = "404",
            description = "Book not found",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))
    )
    public ResponseEntity<Void> deleteBook (@PathVariable Long id){
        service.deleteBook(id);
        return ResponseEntity.noContent().build();
    }
}
