package loan.system.com.User.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import loan.system.com.User.MapperUser.UserMapper;
import loan.system.com.User.domain.User;
import loan.system.com.User.dto.UserRequestDTO;
import loan.system.com.User.dto.UserResponseDTO;
import loan.system.com.User.service.UserService;
import loan.system.com.exception.ErrorResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("user")
@Tag(name = "Users", description = "endpoint for managing Users")
public class UserController {

    private final UserService service;
    private final UserMapper mapper;

    public UserController(UserService service, UserMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping
    @Operation(
            summary = "Find all users",
            description = "return one list of book ",
            tags = {"Users"}
    )
    @ApiResponse(
            responseCode = "200",
            description = "List of users sucessfuly on return",
            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
            array = @ArraySchema(schema = @Schema(implementation = UserResponseDTO.class))
            ))
    public List<UserResponseDTO> findAll (){
        return service.findAll()
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "searches for the user by ID.",
            description = "Search for the specific user by the given ID.")
    @ApiResponse(
            responseCode = "200",
            description = "user found",
            content = @Content(schema = @Schema(implementation = UserResponseDTO.class)
            ))

    @ApiResponse(
            responseCode = "404",
            description = "user not found",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))
    )
    public UserResponseDTO findById (@PathVariable Long id){
        User user = service.findById(id);
        return mapper.toResponse(user);
    }

    @PostMapping
    @Operation(
            summary = "create one new User",
            description = "add one new user to the system"
    )
    @ApiResponse(
            responseCode = "201",
            description = "User create with sucessfuly",
            content = @Content(schema = @Schema(implementation = UserResponseDTO.class))
    )
    @ApiResponse(
            responseCode = "400",
            description = "inválid data for creating the user",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))
    )

    @RequestBody(
            description = "User data for creating.",
            required = true,
            content = @Content(schema = @Schema(implementation = UserRequestDTO.class))
    )
    public UserResponseDTO createUser (@org.springframework.web.bind.annotation.RequestBody @Valid UserRequestDTO dto){
        User user = mapper.toEntity(dto);
        User create = service.createUser(user);
        return mapper.toResponse(create);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "User data update.",
            description = "Update an existing user by ID."
    )
    @ApiResponse(
            responseCode = "200",
            description = "data of the user updating a sucessfully.",
            content = @Content(schema = @Schema(implementation = UserResponseDTO.class))
    )

    @ApiResponses({
            @ApiResponse(responseCode = "404", description = "inválid id, user not found.",
                content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "400", description = "The data you entered may be invalid. Please try again with the correct data.",
                content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "409", description = "The email address you entered already exists.",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @RequestBody(
            description = "User data for field update",
            required = true,
            content = @Content(schema = @Schema(implementation = UserRequestDTO.class))
    )

    public UserResponseDTO updatingUser (@PathVariable Long id,
                                         @org.springframework.web.bind.annotation.RequestBody @Valid UserRequestDTO dto)
    {
        User user = mapper.toEntity(dto);
        User find = service.updateUser(id, user);
        return mapper.toResponse(find);
    }

    @DeleteMapping("/{id}/delete")
    @Operation(
            summary = "delete the user by ID.",
            description = "delete the existing user by ID."
    )
    @ApiResponse(
            responseCode = "204",
            description = "User excluded on sucessfully."
    )

    @ApiResponse(
            responseCode = "404",
            description = "User not found for the ID.",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))
    )
    public ResponseEntity<Void> deleteUser (@PathVariable Long id){
        service.deleteUser(id);
        return ResponseEntity.noContent().build();
    }


}
