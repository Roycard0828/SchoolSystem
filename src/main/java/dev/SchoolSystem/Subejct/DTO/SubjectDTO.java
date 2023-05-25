package dev.SchoolSystem.Subejct.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@Getter
@Setter
public class SubjectDTO {

    @NotBlank
    private String name;
    @NotNull
    private int identifier;
    @NotNull
    private int credits;

}
