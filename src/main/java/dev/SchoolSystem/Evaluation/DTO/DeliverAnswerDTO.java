package dev.SchoolSystem.Evaluation.DTO;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@Getter
public class DeliverAnswerDTO {

    @NotBlank
    private String studentIdentifier;
    @NotNull
    private Long examId;
    private String content;

    @JsonCreator
    public DeliverAnswerDTO(String studentIdentifier, @JsonProperty("examId") Long examId){
        this.studentIdentifier = studentIdentifier;
        this.examId = examId;
    }


}
