package loan.system.com.User.domain;

import jakarta.persistence.*;
import loan.system.com.Loan.domain.Loan;
import loan.system.com.User.UserStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Objects;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "Users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 220)
    private String name;

    @Column(unique = true, nullable = false, length = 200)
    private String email;

    @Enumerated(EnumType.STRING)
    private UserStatus status;

    @OneToMany(mappedBy = "user")
    private List<Loan> loan;


    public User(String name, String email, UserStatus status) {
        this.name = name;
        this.email = email;
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof User user)) return false;
        return Objects.equals(getId(), user.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }
}
