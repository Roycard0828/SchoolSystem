package dev.SchoolSystem.Classroom.Entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import dev.SchoolSystem.Subejct.Entity.Subject;
import dev.SchoolSystem.Teacher.Entity.Teacher;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "classroom")
@Getter @Setter
@AllArgsConstructor
public class Classroom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "class_code")
    private String classCode;

    @OneToOne(targetEntity = Subject.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "subject_id")
    private Subject subject;

    @OneToOne(targetEntity = Teacher.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;

    @OneToOne(mappedBy = "classroom", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private Record record;

    public Classroom(String classCode, Subject subject, Teacher teacher) {
        this.classCode = classCode;
        this.subject = subject;
        this.teacher = teacher;
    }

    public Classroom(){}

    @JsonManagedReference
    public Record getRecord(){
        return record;
    }
}
