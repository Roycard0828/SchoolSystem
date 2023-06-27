//DTO to create a new Activity

package dev.SchoolSystem.Evaluation.DTO.Activity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import dev.SchoolSystem.Evaluation.Entity.ActDelivery;
import dev.SchoolSystem.Evaluation.Entity.Activity;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@Getter
public class ActivityDTO {

    private Long activity_id;
    @NotBlank
    private String description;
    private Set<ActDeliveryDTO> deliveries = new HashSet<>();
    private String record_class_code;

    //Constructor to create a new activity.
    public ActivityDTO(String description, String record_class_code){
        this.description = description;
        this.record_class_code = record_class_code;
    }

    //Constructor to pre-view an activity
    @JsonCreator
    public ActivityDTO(@JsonProperty("activity_id")Long activity_id, String description){
        this.activity_id = activity_id;
        this.description = description;
    }

    //Constructor to view a whole activity
    public ActivityDTO(String description, Set<ActDeliveryDTO> deliveries) {
        this.description = description;
        this.deliveries = deliveries;
    }

    public ActivityDTO(){}

}
