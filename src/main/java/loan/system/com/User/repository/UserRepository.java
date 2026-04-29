package loan.system.com.User.repository;

import loan.system.com.User.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository <User, Long> {
    boolean existsByEmail(String email);
}
