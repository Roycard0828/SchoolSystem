package dev.SchoolSystem.Auth.Repository;

import dev.SchoolSystem.Auth.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);

}
