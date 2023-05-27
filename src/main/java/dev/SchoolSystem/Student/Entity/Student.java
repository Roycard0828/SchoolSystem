package dev.SchoolSystem.Student.Entity;

import dev.SchoolSystem.Auth.Entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "student")
@Getter @Setter
@AllArgsConstructor
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long identifier;
    @Column(name = "age")
    private int age;
    @Column(name = "course")
    private String course;
    @Column(name = "email")
    private String email;
    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_id")
    private User user_id;

    public Student(int age, String course, String email, User user_id) {
        this.age = age;
        this.course = course;
        this.email = email;
        this.user_id = user_id;
    }

    @Override
    public String toString() {
        return "Student{" +
                "identifier=" + identifier +
                ", age=" + age +
                ", course='" + course + '\'' +
                ", email='" + email + '\'' +
                ", user_id=" + user_id +
                '}';
    }
}
