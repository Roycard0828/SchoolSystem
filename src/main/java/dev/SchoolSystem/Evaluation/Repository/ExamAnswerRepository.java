package dev.SchoolSystem.Evaluation.Repository;

import dev.SchoolSystem.Evaluation.Entity.ActDelivery;
import dev.SchoolSystem.Evaluation.Entity.Activity;
import dev.SchoolSystem.Evaluation.Entity.Exam;
import dev.SchoolSystem.Evaluation.Entity.ExamAnswer;
import dev.SchoolSystem.Student.Entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;

public interface ExamAnswerRepository extends JpaRepository<ExamAnswer, Long> {

    @Query("SELECT ea FROM ExamAnswer ea WHERE ea.exam = ?1 AND ea.student = ?2")
    ExamAnswer findByExamAndStudent(Exam exam, Student student);

    @Query("SELECT ea FROM ExamAnswer ea WHERE ea.exam = ?1")
    Set<ExamAnswer> findAllByExam(Exam exam);

}
