package loan.system.com.User.service;

import loan.system.com.User.domain.User;
import loan.system.com.User.dto.UserRequestDTO;
import loan.system.com.User.repository.UserRepository;
import loan.system.com.exception.BadRequestException;
import loan.system.com.exception.ConflictRequestException;
import loan.system.com.exception.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public List<User> findAll(){
        return repository.findAll();
    }

    public User findById(Long id){
        User user = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found"));
        return repository.save(user);
    }
    public User createUser (User user){
        if (user.getName() == null || user.getName().isBlank()){
            throw new BadRequestException("Required one name");
        }
        if (repository.existsByEmail(user.getEmail())){
            throw new ConflictRequestException("Email already registered");
        }
        User userSave = new User(
                user.getName(),
                user.getEmail()
        );
        return repository.save(userSave);
    }

    public User updateUser (Long id, User user){
        User find = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found"));
        user.setName(user.getName());
        return repository.save(user);

    }

    public void deleteUser (Long id){
        User user = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found"));
        repository.delete(user);
    }
    
}
