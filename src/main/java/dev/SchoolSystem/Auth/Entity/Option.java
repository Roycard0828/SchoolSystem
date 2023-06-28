package dev.SchoolSystem.Auth.Entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table
public class Option {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "option_name")
    @NotNull
    private String optionName;
    @Column(name = "path")
    @NotNull
    private String path;
    @ManyToOne(fetch = FetchType.EAGER)
    private Role role;

    public Option() {
    }

    public Option(String optionName, String path, Role role) {
        this.optionName = optionName;
        this.path = path;
        this.role = role;
    }

    public Option(Long id, String optionName, String path, Role role) {
        this.id = id;
        this.optionName = optionName;
        this.path = path;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOptionName() {
        return optionName;
    }

    public void setOptionName(String optionName) {
        this.optionName = optionName;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "Option{" +
                "id=" + id +
                ", optionName='" + optionName + '\'' +
                ", path='" + path + '\'' +
                '}';
    }
}
