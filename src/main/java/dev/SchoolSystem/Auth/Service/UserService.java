package dev.SchoolSystem.Auth.Service;

import dev.SchoolSystem.Auth.DTO.NewUserDTO;
import dev.SchoolSystem.Auth.Entity.Role;
import dev.SchoolSystem.Auth.Entity.User;
import dev.SchoolSystem.Auth.Enums.RoleName;
import dev.SchoolSystem.Auth.Repository.RoleRepository;
import dev.SchoolSystem.Auth.Repository.UserRepository;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.passay.CharacterData;
import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.PasswordGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public User registerUser(NewUserDTO newUser){
        log.info("Registering new user with ROLE_USER");
        String generatePassword = generateFirstPassword();
        User user = new User(newUser.getName(), newUser.getLast_name(),
                newUser.getUsername(), passwordEncoder.encode("1234"));

        Role role = roleRepository.findByRoleName(RoleName.ROLE_USER);
        user.getRoles().add(role);
        log.info("User created with credentials {}", user.toString());
        log.info("Password created to this user {}", generatePassword);
        return userRepository.save(user);
    }

    public String generateFirstPassword(){
        PasswordGenerator generator = new PasswordGenerator();
        CharacterData lowerCaseChars = EnglishCharacterData.LowerCase;
        CharacterRule lowerCaseRule = new CharacterRule(lowerCaseChars);
        lowerCaseRule.setNumberOfCharacters(10);

        return generator.generatePassword(10, lowerCaseRule);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if(user != null){
            log.info("User found in the database: {}", user);
        }else{
            log.error("User not found in the database");
            throw new UsernameNotFoundException("User not found in the database");
        }

        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(
                role -> {authorities.add(
                        new SimpleGrantedAuthority(role.getRoleName().toString()));
                });

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
    }

    public User getUser(String username){
        return userRepository.findByUsername(username);
    }

}
