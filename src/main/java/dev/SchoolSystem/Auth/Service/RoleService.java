package dev.SchoolSystem.Auth.Service;

import dev.SchoolSystem.Auth.DTO.RoleNameDTO;
import dev.SchoolSystem.Auth.Entity.Role;
import dev.SchoolSystem.Auth.Entity.User;
import dev.SchoolSystem.Auth.Enums.RoleName;
import dev.SchoolSystem.Auth.Repository.OptionRepository;
import dev.SchoolSystem.Auth.Repository.RoleRepository;
import dev.SchoolSystem.Auth.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class RoleService {

    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final RoleRepository roleRepository;
    @Autowired
    private final OptionRepository optionRepository;

    public Role createRole(RoleNameDTO roleName){
        Role role = new Role(roleName.getRoleName());
        log.info("Role {} created", role.getRoleNameAsString());
        return roleRepository.save(role);
    }

    public User addRoleTeacherToUser(String username){
        User user = userRepository.findByUsername(username);
        Role role = roleRepository.findByRoleName(RoleName.ROLE_TEACHER);
        user.getRoles().add(role);
        //Add available options for this role
        user.setOptions(optionRepository.getAllOptionsByRoleName(role));

        return userRepository.save(user);
    }

    public User addRoleAndOptionsStudentToUser(String username){
        User user = userRepository.findByUsername(username);
        Role role = roleRepository.findByRoleName(RoleName.ROLE_STUDENT);
        user.getRoles().add(role);
        //Add available options for this role
        user.setOptions(optionRepository.getAllOptionsByRoleName(role));

        return userRepository.save(user);
    }

    public User addRoleAndOptionsManagerToUser(String username){
        User user = userRepository.findByUsername(username);
        Role role = roleRepository.findByRoleName(RoleName.ROLE_MANAGER);
        user.getRoles().add(role);
        //Add available options for this role
        user.setOptions(optionRepository.getAllOptionsByRoleName(role));

        return userRepository.save(user);
    }
}
