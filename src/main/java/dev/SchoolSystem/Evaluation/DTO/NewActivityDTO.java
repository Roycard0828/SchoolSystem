package dev.SchoolSystem.Evaluation.DTO;

import dev.SchoolSystem.Evaluation.Entity.ActDelivery;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@Getter
public class NewActivityDTO {

    @NotBlank
    private String description;
    private Set<ActDelivery> deliveries = new HashSet<>();
    private String record_class_code;

    public NewActivityDTO(String description, String record_class_code){
        this.description = description;
        this.record_class_code = record_class_code;
    }

    public NewActivityDTO(){}

}
