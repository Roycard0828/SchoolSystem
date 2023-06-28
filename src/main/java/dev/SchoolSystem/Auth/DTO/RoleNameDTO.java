package dev.SchoolSystem.Auth.DTO;

import dev.SchoolSystem.Auth.Enums.RoleName;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class RoleNameDTO {

    @NotBlank
    public RoleName roleName;
    public RoleNameDTO(RoleName roleName) {
        this.roleName = roleName;
    }

    public RoleName getRoleName() {
        return roleName;
    }

    public void setRoleName(RoleName roleName) {
        this.roleName = roleName;
    }
}
