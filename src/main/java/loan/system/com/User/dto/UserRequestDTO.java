package loan.system.com.User.dto;

import jakarta.validation.constraints.NotBlank;

public class UserRequestDTO {

    @NotBlank(message = "An email address is required")
    private String email;
    @NotBlank (message = "The name field cannot by empty")
    private String name;

    public UserRequestDTO(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public UserRequestDTO() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
