//DTO only used to create an exam answer for each student, without content.

package dev.SchoolSystem.Evaluation.DTO.Exam;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import dev.SchoolSystem.Student.DTO.ProfileStudentDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@Getter
public class ExamAnswerDTO {

    @NotBlank
    private String student_identifier;
    @NotNull
    private Long exam_id;
    private double note = 0;
    private String content;
    private ProfileStudentDTO profile_student;

    //Constructor used to search a exam answer.
    @JsonCreator
    public ExamAnswerDTO(String student_identifier, @JsonProperty("exam_id") Long exam_id){
        this.student_identifier = student_identifier;
        this.exam_id = exam_id;
    }

    //Constructor to assign a note to a exam.
    public ExamAnswerDTO(String student_identifier, Long exam_id, double note) {
        this.student_identifier = student_identifier;
        this.exam_id = exam_id;
        this.note = note;
    }

    //Constructor to deliver an answer
    public ExamAnswerDTO(String student_identifier, Long exam_id, String content) {
        this.student_identifier = student_identifier;
        this.exam_id = exam_id;
        this.content = content;
    }

    public ExamAnswerDTO(){}
}