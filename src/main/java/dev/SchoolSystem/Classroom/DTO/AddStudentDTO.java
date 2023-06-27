package dev.SchoolSystem.Classroom.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
@AllArgsConstructor
public class AddStudentDTO {

    @NotBlank
    private String class_code;
    @NotBlank
    private String student_identifier;

}
