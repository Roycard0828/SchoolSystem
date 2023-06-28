package dev.SchoolSystem.Subejct.Entity;

import javax.persistence.*;

@Entity
@Table(name = "subject")
public class Subject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "identifier", unique = true)
    private String identifier;
    @Column(name = "name")
    private String name;
    @Column(name = "credits")
    private int credits;

    public Subject(){

    }

    public Subject(String identifier, String name, int credits) {
        this.identifier = identifier;
        this.name = name;
        this.credits = credits;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    @Override
    public String toString() {
        return "Subject{" +
                "id=" + id +
                ", identifier=" + identifier +
                ", name='" + name + '\'' +
                ", credits=" + credits +
                '}';
    }
}
