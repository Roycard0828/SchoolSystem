package dev.SchoolSystem.Evaluation.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@Getter
public class ActDeliveryDTO {

    @NotBlank
    private String studentIdentifier;
    @NotNull
    private Long activityId;

}
