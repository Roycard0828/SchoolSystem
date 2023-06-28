package dev.SchoolSystem.Auth.DTO;

import dev.SchoolSystem.Auth.Enums.RoleName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@Getter
public class NewOptionDTO {

    @NotNull
    private String name;
    @NotNull
    private String path;
    @NotNull
    private RoleName roleName;

}
