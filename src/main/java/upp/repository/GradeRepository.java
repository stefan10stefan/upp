package upp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import upp.model.Book;
import upp.model.Grade;
import upp.model.User;

import java.util.List;

@Repository
public interface GradeRepository extends JpaRepository<Grade, Long> {

    public Grade findByWriterIdAndDirectorId(Long writerId, Long directorId);
    public List<Grade> findAllByWriterId(Long writerId);
}
