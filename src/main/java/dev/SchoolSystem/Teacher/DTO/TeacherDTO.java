package dev.SchoolSystem.Teacher.DTO;

import dev.SchoolSystem.Auth.Entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@Getter @Setter
public class TeacherDTO {

    private String username;
    @NotBlank
    private String identifier;
    @NotNull
    private int age;
    @NotBlank
    private String professional_title;
    @NotBlank
    private String email;

    public TeacherDTO(String identifier, int age, String professional_title, String email) {
        this.identifier = identifier;
        this.age = age;
        this.professional_title = professional_title;
        this.email = email;
    }

    public TeacherDTO(){}
}
