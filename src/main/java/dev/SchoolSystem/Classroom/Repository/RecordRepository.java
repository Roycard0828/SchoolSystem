package dev.SchoolSystem.Classroom.Repository;

import dev.SchoolSystem.Classroom.Entity.Classroom;
import dev.SchoolSystem.Classroom.Entity.Record;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface RecordRepository extends JpaRepository<Record, Long> {

    @Query("SELECT r FROM Record r WHERE r.classroom=?1")
    Optional<Record> findRecordByClassroom(Classroom classroom);

    @Query("SELECT r FROM Record r WHERE r.classroom.classCode=?1")
    Optional<Record> findRecordByClassCode(String classCode);

}
