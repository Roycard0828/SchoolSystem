package dev.SchoolSystem.Evaluation.Entity;

import dev.SchoolSystem.Student.Entity.Student;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "activity_delivery")
@Setter @Getter
@AllArgsConstructor
public class ActDelivery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "content")
    private String content;
    @Column(name = "comments")
    private String comments;
    @Column(name = "note")
    private double note;
    @Column(name = "delivery_date")
    private Date deliveryDate;

    @ManyToOne
    @JoinColumn(name = "activity_id", nullable = false)
    private Activity activity;

    @OneToOne(targetEntity = Student.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "student_id")
    private Student student;

    public ActDelivery(Student student, Activity activity) {
        this.student = student;
        this.activity = activity;
    }


}
