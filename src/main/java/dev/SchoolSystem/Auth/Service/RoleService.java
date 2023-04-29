package dev.SchoolSystem.Auth.Service;

import dev.SchoolSystem.Auth.Entity.Role;
import dev.SchoolSystem.Auth.Entity.User;
import dev.SchoolSystem.Auth.Enums.RoleName;
import dev.SchoolSystem.Auth.Repository.RoleRepository;
import dev.SchoolSystem.Auth.Repository.UserRepository;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@NoArgsConstructor
@Transactional
@Slf4j
public class RoleService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    void addRoleTeacherToUser(String username){
        User user = userRepository.findByUsername(username);
        Role role = roleRepository.findByName(RoleName.ROLE_TEACHER);
        user.getRoles().add(role);

        userRepository.save(user);
    }

    void addRoleStudentToUser(String username){
        User user = userRepository.findByUsername(username);
        Role role = roleRepository.findByName(RoleName.ROLE_STUDENT);
        user.getRoles().add(role);

        userRepository.save(user);
    }

}
