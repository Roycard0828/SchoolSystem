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

    @NotBlank
    private String identifier;
    @NotNull
    private int age;
    @NotBlank
    private String professional_title;
    @NotBlank
    private String email;
    @NotBlank
    private User user_id;

}
