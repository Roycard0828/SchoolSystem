//DTO to create an empty activity for each student.

package dev.SchoolSystem.Evaluation.DTO.Activity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import dev.SchoolSystem.Student.DTO.ProfileStudentDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@AllArgsConstructor
@Getter
public class ActDeliveryDTO {

    @NotBlank
    private String student_identifier;
    @NotNull
    private Long activity_id;
    private double note;
    private String content;
    private Date delivery_date;
    private ProfileStudentDTO profileStudent;

    //Constructor to find a ActDelivery
    @JsonCreator
    public ActDeliveryDTO(String student_identifier, @JsonProperty("activity_id") Long activity_id){
        this.student_identifier = student_identifier;
        this.activity_id = activity_id;
    }

    //Constructor to assign note to an act delivery
    public ActDeliveryDTO(String student_identifier, Long activity_id,
                          double note){
        this.student_identifier = student_identifier;
        this.activity_id = activity_id;
        this.note = note;
    }

    //Constructor to deliver an activity
    public ActDeliveryDTO(String student_identifier, Long activity_id,
                          String content, Date delivery_date){
        this.student_identifier = student_identifier;
        this.activity_id = activity_id;
        this.content = content;
        this.delivery_date = delivery_date;
    }

    public ActDeliveryDTO(){}
}
