package dev.SchoolSystem.Classroom.Entity;

import dev.SchoolSystem.Student.Entity.Student;
import dev.SchoolSystem.Subejct.Entity.Subject;
import dev.SchoolSystem.Teacher.Entity.Teacher;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "class")
@Getter @Setter
@AllArgsConstructor
public class Classroom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "number")
    private int number;
    @OneToOne(targetEntity = Subject.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "subject_id")
    private Subject subject;
    @OneToOne(targetEntity = Teacher.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "teacher_id")
    private Teacher teacher;
    @OneToOne(targetEntity = Student.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "student_id")
    private Set<Student> students;

    public Classroom(int number, Subject subject, Teacher teacher, Set<Student> students) {
        this.number = number;
        this.subject = subject;
        this.teacher = teacher;
        this.students = students;
    }

    @Override
    public String toString() {
        return "Class{" +
                "id=" + id +
                ", number=" + number +
                ", subject=" + subject +
                ", teacher=" + teacher +
                ", students=" + students +
                '}';
    }
}
