package dev.SchoolSystem.Evaluation.DTO;

import dev.SchoolSystem.Evaluation.Entity.ExamAnswer;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@Getter
public class NewExamDTO {

    private String description;
    private String content;
    private Set<ExamAnswer> answers = new HashSet<>();
    private String classCode;

    public NewExamDTO(String description, String content, String classCode){
        this.description = description;
        this.content = content;
        this.classCode = classCode;
    }

}
