package dev.SchoolSystem.Classroom.DTO;

import dev.SchoolSystem.Classroom.Entity.Record;
import dev.SchoolSystem.Student.Entity.Student;
import dev.SchoolSystem.Subejct.Entity.Subject;
import dev.SchoolSystem.Teacher.Entity.Teacher;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.util.Set;

@AllArgsConstructor
@Getter
public class ClassroomDTO {

    @NotNull
    private int number;
    @NotNull
    private Subject subject;
    @NotNull
    private Teacher teacher;
    @NotNull
    private Record record;

}
