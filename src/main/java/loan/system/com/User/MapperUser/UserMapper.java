package loan.system.com.User.MapperUser;

import loan.system.com.User.domain.User;
import loan.system.com.User.dto.UserRequestDTO;
import loan.system.com.User.dto.UserResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserResponseDTO toResponse (User user){
        UserResponseDTO dto = new UserResponseDTO();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        return dto;
    }

    public User toEntity (UserRequestDTO dto){
        User user = new User();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        return user;
    }

}
