package dev.SchoolSystem.Evaluation.Entity;

import dev.SchoolSystem.Student.Entity.Student;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "activity_delivery")
@Setter @Getter
@AllArgsConstructor
public class ActivityDelivery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "content")
    private String content;
    @Column(name = "student")
    private String comments;
    @Column(name = "note")
    private double note;

    @ManyToOne
    @JoinColumn(name = "activity_id", nullable = false)
    private Activity activity;

    @OneToOne(targetEntity = Student.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "student_id")
    private Student student;

    public ActivityDelivery(Student student, String content, String comments, double note) {
        this.student = student;
        this.content = content;
        this.comments = comments;
        this.note = note;
    }


}
