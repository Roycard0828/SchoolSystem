package dev.SchoolSystem.Manager.Entity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "manager")
public class Manager {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "last_name")
    private String last_name;
    @Column(name = "identifier")
    private String identifier;
    @Column(name = "password")
    private String password;

    public Manager(){}

    public Manager(Long id, String name, String last_name, String identifier, String password) {
        this.id = id;
        this.name = name;
        this.last_name = last_name;
        this.identifier = identifier;
        this.password = password;
    }

    public Manager(String name, String last_name, String identifier, String password) {
        this.name = name;
        this.last_name = last_name;
        this.identifier = identifier;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Manager{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", identifier='" + identifier + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
