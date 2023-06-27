package dev.SchoolSystem.Classroom.DTO;

import dev.SchoolSystem.Evaluation.DTO.Activity.ActivityDTO;
import dev.SchoolSystem.Evaluation.DTO.Exam.ExamDTO;
import dev.SchoolSystem.Evaluation.Entity.Activity;
import dev.SchoolSystem.Evaluation.Entity.Exam;
import dev.SchoolSystem.Student.DTO.ProfileStudentDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Set;


@AllArgsConstructor
@Getter
public class RecordResponse {

    private Long id;
    private Set<ProfileStudentDTO> students;

}
