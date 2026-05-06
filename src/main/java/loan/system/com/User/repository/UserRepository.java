package loan.system.com.User.repository;

import loan.system.com.User.UserStatus;
import loan.system.com.User.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository <User, Long> {
    boolean existsByEmail(String email);
    List<User> findByStatus (UserStatus status);
    boolean findByIdAndStatus(Long userId, UserStatus status);
}
