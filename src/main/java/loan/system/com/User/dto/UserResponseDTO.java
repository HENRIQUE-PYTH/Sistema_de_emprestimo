package loan.system.com.User.dto;

public class UserResponseDTO {

    private Long id;
    private String email;
    private String name;

    public UserResponseDTO(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public UserResponseDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
