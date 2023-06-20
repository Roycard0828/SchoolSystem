package dev.SchoolSystem.Evaluation.DTO;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@AllArgsConstructor
@Getter
public class ActDeliveryDTO {

    @NotBlank
    private String studentIdentifier;
    @NotNull
    private Long activityId;
    private double note = 0;

    @JsonCreator
    public ActDeliveryDTO(String studentIdentifier,@JsonProperty("activityId") Long activityId){
        this.studentIdentifier = studentIdentifier;
        this.activityId = activityId;
    }


}
