package loan.system.com.User.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserRequestDTO {

    @NotBlank(message = "An email address is required")
    private String email;
    @NotBlank (message = "The name field cannot by empty")
    private String name;

}
