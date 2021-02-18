package upp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import upp.model.User;
import upp.model.UserType;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    public User findByRegistrationToken(String token);
    public User findByEmail(String email);
    public List<User> findAllByCanGrade(boolean canGrade);
    public List<User> findAllByUserType(UserType userType);
}
