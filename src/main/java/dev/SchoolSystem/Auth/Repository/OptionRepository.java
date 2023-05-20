package dev.SchoolSystem.Auth.Repository;

import dev.SchoolSystem.Auth.Entity.Option;
import dev.SchoolSystem.Auth.Entity.Role;
import dev.SchoolSystem.Auth.Enums.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

@Repository
public interface OptionRepository extends JpaRepository<Option, Long> {

    @Query("SELECT o FROM Option o WHERE o.role=?1")
    Set<Option> getAllOptionsByRoleName(Role role);

}
