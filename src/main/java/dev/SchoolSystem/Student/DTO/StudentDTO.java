package dev.SchoolSystem.Student.DTO;

import dev.SchoolSystem.Auth.Entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@Getter
public class StudentDTO {

    @NotBlank
    private String identifier;
    @NotNull
    private int age;
    @NotBlank
    private String course;
    @NotBlank
    private String email;
    @NotNull
    private User user_id;

}
