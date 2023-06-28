package dev.SchoolSystem.Teacher.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ProfileTeacherDTO {

    private String identifier;
    private String name;
    private String last_name;
    private String username;
    private int age;
    private String professional_title;
    private String email;

}
