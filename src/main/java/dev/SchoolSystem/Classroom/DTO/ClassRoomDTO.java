package dev.SchoolSystem.Classroom.DTO;

import dev.SchoolSystem.Classroom.Entity.Record;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@Getter
public class ClassRoomDTO {

    @NotBlank
    private String classCode;
    @NotBlank
    private String subjectIdentifier;
    @NotBlank
    private String teacherIdentifier;

}
