package dev.SchoolSystem.Student.DTO;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import dev.SchoolSystem.Auth.Entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@Getter
public class StudentDTO {

    private String username;
    @NotBlank
    private String identifier;
    @NotNull
    private int age;
    @NotBlank
    private String course;
    @NotBlank
    private String email;

    @JsonCreator()
    public StudentDTO(String identifier, @JsonProperty("age") int age, String course, String email) {
        this.identifier = identifier;
        this.age = age;
        this.course = course;
        this.email = email;
    }
}
