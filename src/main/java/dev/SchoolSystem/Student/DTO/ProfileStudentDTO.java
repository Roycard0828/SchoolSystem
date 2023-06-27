package dev.SchoolSystem.Student.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ProfileStudentDTO {

    private String identifier;
    private String name;
    private String last_name;
    private String username;
    private int age;
    private String course;
    private String email;

}
