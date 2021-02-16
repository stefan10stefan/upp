package upp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import upp.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    public User findByRegistrationToken(String token);
    public User findByEmail(String email);
}
