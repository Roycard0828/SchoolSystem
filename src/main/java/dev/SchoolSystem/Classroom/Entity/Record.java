package dev.SchoolSystem.Classroom.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import dev.SchoolSystem.Evaluation.Entity.Activity;
import dev.SchoolSystem.Evaluation.Entity.Exam;
import dev.SchoolSystem.Student.Entity.Student;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "record")
@Getter @Setter
@AllArgsConstructor
public class Record {

    //Following the Shared Primary Key strategy
    @Id
    @Column(name = "classroom_id")
    private Long id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "classroom_id")
    private Classroom classroom;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "record_student", joinColumns = @JoinColumn(name = "record_id"),
            inverseJoinColumns = @JoinColumn(name = "student_id"))
    private Set<Student> students;

    @OneToMany(mappedBy = "record", cascade = CascadeType.ALL)
    private Set<Activity> activities;

    @OneToMany(mappedBy = "record", cascade = CascadeType.ALL)
    private Set<Exam> exams;

    public Record(){
    }

    public Record(Classroom classroom, Set<Student> students) {
        this.classroom = classroom;
        this.students = students;
    }

    public Record(Classroom classroom, Set<Student> students, Set<Activity> activities, Set<Exam> exams) {
        this.classroom = classroom;
        this.students = students;
        this.activities = activities;
        this.exams = exams;
    }

    @JsonBackReference
    public Classroom getClassroom(){
        return this.classroom;
    }
}
