package dev.SchoolSystem.Teacher.Repository;

import dev.SchoolSystem.Teacher.Entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {

    Teacher findByIdentifier(String identifier);

}
