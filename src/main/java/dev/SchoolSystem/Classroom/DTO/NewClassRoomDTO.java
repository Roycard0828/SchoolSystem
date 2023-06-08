package dev.SchoolSystem.Classroom.DTO;

import dev.SchoolSystem.Classroom.Entity.Record;
import dev.SchoolSystem.Subejct.Entity.Subject;
import dev.SchoolSystem.Teacher.Entity.Teacher;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@Getter
public class NewClassRoomDTO {

    @NotBlank
    private String classCode;
    @NotNull
    private Subject subject;
    @NotNull
    private Teacher teacher;;

}
