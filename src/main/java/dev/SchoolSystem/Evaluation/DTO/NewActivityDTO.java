package dev.SchoolSystem.Evaluation.DTO;

import dev.SchoolSystem.Classroom.Entity.Record;
import dev.SchoolSystem.Evaluation.Entity.ActDelivery;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@Getter
public class NewActivityDTO {

    @NotBlank
    private String description;
    private Set<ActDelivery> deliveries = new HashSet<>();
    @NotNull
    private Record record;

    public NewActivityDTO(String description, Record record){
        this.description = description;
        this.record = record;
    }

}
