package loan.system.com.User.service;

import loan.system.com.Loan.service.LoanRuleService;
import loan.system.com.User.UserStatus;
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
    private final LoanRuleService service;

    public UserService(UserRepository repository, LoanRuleService service) {
        this.repository = repository;
        this.service = service;
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

        UserStatus status = UserStatus.ACTIVE;

        User userSave = new User(
                user.getName(),
                user.getEmail(),
                status
        );
        return repository.save(userSave);
    }

    public User updateUser (Long id, User user){
        User find = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found"));
        find.setName(user.getName());
        return repository.save(find);

    }

    public void deleteUser (Long id){
        User user = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found"));
        repository.delete(user);
    }

    public List<User> allUsersBlocked(){
        return repository.findByStatus(UserStatus.BLOCKED);
    }

    public boolean isUserBlocked (Long userId){
        return repository.findByIdAndStatus(userId, UserStatus.BLOCKED);
    }

    public User unblockUser (Long id){
        User user = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found"));
        user.setStatus(UserStatus.ACTIVE);
        return repository.save(user);
    }

    
}
