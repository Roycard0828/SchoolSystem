package dev.SchoolSystem.Auth.Repository;

import dev.SchoolSystem.Auth.Entity.Role;
import dev.SchoolSystem.Auth.Enums.RoleName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class RoleRepositoryTest {

    @Autowired
    RoleRepository roleRepository;

    @Test
    void findByRoleName(){
        //given
        Role createdRole = roleRepository.save(new Role(RoleName.ROLE_USER));
        //when
        Role role = roleRepository.findByRoleName(RoleName.ROLE_USER);
        //then
        assertEquals(RoleName.ROLE_USER, role.getRoleName());
    }

}