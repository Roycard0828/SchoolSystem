package dev.SchoolSystem.Auth.Repository;

import dev.SchoolSystem.Auth.Entity.Option;
import dev.SchoolSystem.Auth.Entity.Role;
import dev.SchoolSystem.Auth.Enums.RoleName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class OptionRepositoryTest {

    @Autowired
    OptionRepository optionRepository;
    @Autowired
    RoleRepository roleRepository;

    @Test
    void getAllOptionsBySpecificTest(){
        //given
        Role role = new Role(RoleName.ROLE_TEACHER);
        Option option = new Option("Assign note", "/assign/notes", role);
        //when
        roleRepository.save(role);
        optionRepository.save(option);
        Set<Option> options = optionRepository.getAllOptionsByRoleName(role);
        //then
        assertNotNull(options);


    }

}