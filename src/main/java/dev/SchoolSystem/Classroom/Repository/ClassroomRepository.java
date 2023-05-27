package dev.SchoolSystem.Classroom.Repository;

import dev.SchoolSystem.Classroom.Entity.Classroom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClassroomRepository extends JpaRepository<Classroom, Long> {

    Classroom findByNumber(int number);

}
