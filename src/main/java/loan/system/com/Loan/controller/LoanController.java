package loan.system.com.Loan.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import loan.system.com.Loan.LoanMapper.LoanMapper;
import loan.system.com.Loan.domain.Loan;
import loan.system.com.Loan.dto.LoanResponseDTO;
import loan.system.com.Loan.service.LoanService;
import loan.system.com.User.MapperUser.UserMapper;
import loan.system.com.User.dto.UserResponseDTO;
import org.springframework.http.MediaType;
import loan.system.com.exception.ErrorResponse;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@Tag(name = "Loans", description = "endpoint for managing user loans." )
@RequestMapping("/loans")
public class LoanController {

    private final LoanService service;
    private final LoanMapper mapper;
    private final UserMapper userMapper;

    public LoanController(LoanService service, LoanMapper mapper, UserMapper userMapper) {
        this.service = service;
        this.mapper = mapper;
        this.userMapper = userMapper;
    }

    @GetMapping
    @Operation(
            summary = "List of loans",
            description = "Returns a list of all loans."
    )
    @ApiResponse(
            responseCode = "200",
            description = "List of loans successfully returned.",
            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
            array = @ArraySchema(schema = @Schema (implementation = LoanResponseDTO.class))
            ))
    public List<LoanResponseDTO> findAllLoans(){
        return service.findAllLoans()
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    @GetMapping("/loans/active")
    @Operation(
            summary = "list of active loans",
            description = "Returns a list containing only active loans."
    )
    @ApiResponse(
            responseCode = "200",
            description = "List of loans active sucessfully returned",
            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
            array = @ArraySchema(schema = @Schema(implementation = LoanResponseDTO.class))
            ))
    public List<LoanResponseDTO> getAllActiveLoans (){
        return service.getAllActiveLoans()
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    @GetMapping("/loan/{userId}/active")
    @Operation(
            summary = "active business owners of a user.",
            description = "Search for a user by ID to view their active loans."
    )

    @ApiResponse(
            responseCode = "200",
            description = "The user's active loans were successfully returned.",
            content = @Content(schema = @Schema(implementation = LoanResponseDTO.class))
    )
    @ApiResponse(
            responseCode = "404",
            description = "The specified ID was not found.",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))
    )
    public List<LoanResponseDTO> getActiveLoansByUser(@PathVariable Long userId){
        return service.getActiveLoansByUser(userId)
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    @GetMapping("/loan/{userId}/history")
    @Operation(
            summary = "user's business history",
            description = "Search for a user by ID to view their loan history."
    )
    @ApiResponse(
            responseCode = "200",
            description = "The user's history was successfully returned.",
            content = @Content(schema = @Schema(implementation = LoanResponseDTO.class))
    )
    @ApiResponse(
            responseCode = "404",
            description = "We were unable to find a user with that ID.",
            content = @Content(schema = @Schema(implementation = loan.system.com.exception.ErrorResponse.class))
    )
    public List<LoanResponseDTO> getUserLoanHistory (@PathVariable Long userId){
        return service.getUserLoanHistory(userId)
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    @GetMapping("/loans/overdue/users")
    @Operation(
            summary =  "Users with overdue business owners",
            description = "Returns a list of only the users who have overdue loans."
    )
    @ApiResponse(
            responseCode = "200",
            description = "The list of users with overdue loans was successfully returned.",
            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
            array = @ArraySchema(schema = @Schema(implementation = LoanResponseDTO.class)))
    )
    public List<UserResponseDTO> getUsersWithOverdueLoans (){
        return service.getUsersWithOverdueLoans()
                .stream()
                .map(userMapper::toResponse)
                .toList();
    }

    @GetMapping("/pending/{userId}/fines")
    @Operation(
            summary = "outstanding fines",
            description = "Search for a user by ID and add up all their outstanding fines."
    )
    @ApiResponse(
            responseCode = "200",
            description = "The user was found with his outstanding fines.",
            content = @Content(schema = @Schema(implementation = LoanResponseDTO.class))
    )
    @ApiResponse(
            responseCode = "404",
            description = "We were unable to find the user and their outstanding fines using this ID.",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))
    )
    public BigDecimal getTotalPendingFines (@PathVariable Long userId){
        return service.getTotalPendingFines(userId);
    }

    @GetMapping("/{loanId}")
    @Operation(
            summary = "looking for a loan using ID",
            description = "The loan, book, and user are searched by their corresponding ID."
    )
    @ApiResponse(
            responseCode = "200",
            description = "The loan was located, and the user was also found.",
            content = @Content(schema = @Schema(implementation = LoanResponseDTO.class))
    )
    @ApiResponse(
            responseCode = "404",
            description = "We were unable to find a loan with this ID.",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))
    )
    public LoanResponseDTO findById (@PathVariable Long loanId){
        Loan loan = service.findById(loanId);
        return mapper.toResponse(loan);
    }

    @GetMapping("/user/{id}")
    @Operation(
            summary = "Search for the user by ID.",
            description = "searching for a user who took out a loan using ID."
    )
    @ApiResponse(
            responseCode = "200",
            description = "The user was found",
            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
            array = @ArraySchema(schema = @Schema(implementation = LoanResponseDTO.class)))
    )
    @ApiResponse(
            responseCode = "404",
            description = "The user was not found with that ID.",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))
    )
    public List<LoanResponseDTO> findByUser (@PathVariable Long id){
        return service.findByUser(id)
                .stream()
                .map(mapper::toResponse)
                .toList();
    }


    @GetMapping("/overdue/loans")
    @Operation(
            summary = "overdue loans",
            description = "Returns a list of loans that are overdue."
    )
    @ApiResponse(
            responseCode = "200",
            description = "The list of overdue loans was successfully returned.",
            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    array = @ArraySchema(schema = @Schema(implementation = LoanResponseDTO.class)))
    )
    public List<LoanResponseDTO> findOverdueLoans (){
        return service.findOverdueLoans()
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    @PostMapping
    @Operation(
            summary = "create a loan",
            description = "creates a book loan"
    )
    @ApiResponse(
            responseCode = "201",
            description = "The creation of a new loan was successful.",
            content = @Content(schema = @Schema(implementation = LoanResponseDTO.class))
    )
    @ApiResponses({
            @ApiResponse(responseCode = "400", description = "Invalid loan request data.",
                content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "409", description = "User blocked, loan limit reached, or book unavailable.",
                content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "User or book not found",
                content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public LoanResponseDTO createLoan (@PathVariable Long userId, @PathVariable Long bookId){
        Loan loan = service.createLoan(userId, bookId);
        return mapper.toResponse(loan);
    }

    @PostMapping("/loans/{loanId}/return")
    @Operation(
            summary = "loan return",
            description = "the loan that the user took out and was returned"
    )
    @ApiResponse(
            responseCode = "200",
            description = "The loan was successfully repaid.",
            content = @Content(schema = @Schema(implementation = LoanResponseDTO.class))
    )
    @ApiResponse(
            responseCode = "404",
            description = "loan not found",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))
    )
    public LoanResponseDTO returnLoan (@PathVariable Long loanId){
        Loan loan = service.returnLoan(loanId);
        return mapper.toResponse(loan);
    }

}
