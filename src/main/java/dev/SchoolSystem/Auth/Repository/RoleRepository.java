package dev.SchoolSystem.Auth.Repository;

import dev.SchoolSystem.Auth.Entity.Role;
import dev.SchoolSystem.Auth.Enums.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

     Role findByRoleName(RoleName roleName);

}
