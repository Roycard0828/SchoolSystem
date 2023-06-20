package dev.SchoolSystem.Evaluation.DTO;

import dev.SchoolSystem.Evaluation.Entity.ExamAnswer;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@Getter
public class NewExamDTO {

    @NotBlank
    private String description;
    @NotBlank
    private String content;
    private Set<ExamAnswer> answers = new HashSet<>();
    @NotBlank
    private String class_code;

    public NewExamDTO(String description, String content, String class_code){
        this.description = description;
        this.content = content;
        this.class_code = class_code;
    }

    public NewExamDTO(){}

}
