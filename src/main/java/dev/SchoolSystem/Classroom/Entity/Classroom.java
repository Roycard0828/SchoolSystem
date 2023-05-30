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
@Table(name = "classroom")
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

    @OneToOne(mappedBy = "classroom", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private Record record;

    public Classroom(int number, Subject subject, Teacher teacher, Record record) {
        this.number = number;
        this.subject = subject;
        this.teacher = teacher;
        this.record = record;
    }
}
