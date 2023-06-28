package dev.SchoolSystem.Student.Repository;

import dev.SchoolSystem.Auth.Entity.Option;
import dev.SchoolSystem.Student.Entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {

    Optional<Student> findByIdentifier(String identifier);
    @Query("SELECT s FROM Student s WHERE s.course = ?1")
    List<Student> getStudentsByCourse(String course);
}
