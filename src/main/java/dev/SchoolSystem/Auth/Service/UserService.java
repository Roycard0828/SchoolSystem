package dev.SchoolSystem.Auth.Service;

import dev.SchoolSystem.Auth.DTO.NewUserDTO;
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
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    User registerUser(NewUserDTO newUser){
        User user = new User(newUser.getName(), newUser.getLast_name(),
                newUser.getUsername(), newUser.getPassword());

        Role role = roleRepository.findByName(RoleName.ROLE_USER);
        user.getRoles().add(role);

        return userRepository.save(user);
    }



}
