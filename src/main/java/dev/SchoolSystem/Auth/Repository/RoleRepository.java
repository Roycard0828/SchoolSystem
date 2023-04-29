package dev.SchoolSystem.Auth.Repository;

import dev.SchoolSystem.Auth.Entity.Role;
import dev.SchoolSystem.Auth.Enums.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByName(RoleName roleName);

}
