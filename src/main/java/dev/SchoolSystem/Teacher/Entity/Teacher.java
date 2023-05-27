package dev.SchoolSystem.Teacher.Entity;

import dev.SchoolSystem.Auth.Entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "teacher")
@Getter @Setter
@AllArgsConstructor
public class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long identifier;
    @Column(name = "age")
    private int age;
    @Column(name = "professional_title")
    private String professional_title;
    @Column(name = "email")
    private String email;
    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_id")
    private User user_id;

    public Teacher(int age, String professional_title, String email, User user_id) {
        this.age = age;
        this.professional_title = professional_title;
        this.email = email;
        this.user_id = user_id;
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "identifier=" + identifier +
                ", age=" + age +
                ", professional_title='" + professional_title + '\'' +
                ", email='" + email + '\'' +
                ", user_id=" + user_id +
                '}';
    }
}
