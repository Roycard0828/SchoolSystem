package dev.SchoolSystem.Evaluation.Entity;

import dev.SchoolSystem.Student.Entity.Student;
import dev.SchoolSystem.Teacher.Entity.Teacher;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "exam_answer")
@Setter @Getter
@AllArgsConstructor
public class ExamAnswer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "content")
    private String content;
    @Column(name = "note")
    private double note;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "exam_id", nullable = false)
    private Exam exam;

    @OneToOne(targetEntity =Student.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "student_id")
    private Student student;

    public ExamAnswer(Exam exam, Student student) {
        this.exam = exam;
        this.student = student;
    }
}
