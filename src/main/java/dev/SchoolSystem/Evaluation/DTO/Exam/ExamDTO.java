//DTO to create a new Exam
package dev.SchoolSystem.Evaluation.DTO.Exam;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import dev.SchoolSystem.Evaluation.Entity.ExamAnswer;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@Getter
public class ExamDTO {

    private Long exam_id;
    @NotBlank
    private String description;
    @NotBlank
    private String content;
    @NotBlank
    private String class_code;
    private Set<ExamAnswerDTO> answers = new HashSet<>();

    //Constructor to create an Exam
    public ExamDTO(String description, String content, String class_code){
        this.description = description;
        this.content = content;
        this.class_code = class_code;
    }

    //Constructor to read a set of exams
    @JsonCreator
    public ExamDTO(@JsonProperty("exam_id")Long exam_id, String description){
        this.exam_id = exam_id;
        this.description = description;
    }

    //Constructor to response exam.

    public ExamDTO(){}

}
