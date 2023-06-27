package dev.SchoolSystem.Evaluation.Repository;

import dev.SchoolSystem.Classroom.Entity.Record;
import dev.SchoolSystem.Evaluation.Entity.Activity;
import dev.SchoolSystem.Evaluation.Entity.Exam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;

public interface ExamRepository extends JpaRepository<Exam, Long>{

    @Query("SELECT e FROM Exam e WHERE e.record = ?1")
    Set<Exam> findAllByRecord(Record record);

}
