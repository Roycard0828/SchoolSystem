package dev.SchoolSystem.Evaluation.DTO;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@AllArgsConstructor
@Getter
public class DeliverDeliveryDTO {

    @NotBlank
    private String studentIdentifier;
    @NotNull
    private Long activityId;
    private String content = null;
    private Date deliveryDate = null;
    @JsonCreator
    public DeliverDeliveryDTO(String studentIdentifier,@JsonProperty("activityId") Long activityId){
        this.studentIdentifier = studentIdentifier;
        this.activityId = activityId;
    }

    public DeliverDeliveryDTO(){
    }

}
