package upp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import upp.model.Grade;
import upp.model.User;

@Repository
public interface GradeRepository extends JpaRepository<Grade, Long> {
}
