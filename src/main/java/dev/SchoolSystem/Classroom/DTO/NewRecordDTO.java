package dev.SchoolSystem.Classroom.DTO;

import dev.SchoolSystem.Classroom.Entity.Classroom;
import dev.SchoolSystem.Student.Entity.Student;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Getter
public class NewRecordDTO {

    @NotNull
    private Classroom classroom;
    private Set<Student> students = new HashSet<>();

    public NewRecordDTO(Classroom classroom){
        this.classroom = classroom;
    }

}