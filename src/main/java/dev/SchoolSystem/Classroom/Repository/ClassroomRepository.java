package dev.SchoolSystem.Classroom.Repository;

import dev.SchoolSystem.Classroom.Entity.Classroom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClassroomRepository extends JpaRepository<Classroom, Long> {

    Optional<Classroom> findByClassCode(String classCode);

}
