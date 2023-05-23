package dev.SchoolSystem.Auth.Service;

import dev.SchoolSystem.Auth.DTO.RoleNameDTO;
import dev.SchoolSystem.Auth.Entity.Role;
import dev.SchoolSystem.Auth.Entity.User;
import dev.SchoolSystem.Auth.Enums.RoleName;
import dev.SchoolSystem.Auth.Repository.OptionRepository;
import dev.SchoolSystem.Auth.Repository.RoleRepository;
import dev.SchoolSystem.Auth.Repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class RoleServiceTest {

    @Mock
    UserRepository userRepository;
    @Mock
    RoleRepository roleRepository;
    @Mock
    OptionRepository optionRepository;
    @InjectMocks
    RoleService underTest;
    User user;
    private final Role studentRole = new Role(RoleName.ROLE_STUDENT);
    private final Role teacherRole = new Role(RoleName.ROLE_TEACHER);
    private final Role managerRole = new Role(RoleName.ROLE_MANAGER);

    @BeforeEach
    void setUp() {
        underTest = new RoleService(userRepository, roleRepository, optionRepository);
        user = new User("name", "last_name", "username", "password");
    }

    @Test
    void createRoleTest(){
        //given
        RoleNameDTO role = new RoleNameDTO(RoleName.ROLE_USER);
        //when
        underTest.createRole(role);
        ArgumentCaptor<Role> roleArgumentCaptor =
                ArgumentCaptor.forClass(Role.class);
        verify(roleRepository).save(roleArgumentCaptor.capture());
        Role givenRole = roleArgumentCaptor.getValue();
        //then
        assertInstanceOf(Role.class, givenRole);
    }

    @Test
    void addStudentRoleToUserTest(){
        //given
        String username = user.getUsername();
        //when
        given(userRepository.findByUsername(username)).willReturn(user);
        given(roleRepository.findByRoleName(RoleName.ROLE_STUDENT)).willReturn(studentRole);
        given(userRepository.save(user)).willReturn(user);
        User userGiven = underTest.addRoleAndOptionsStudentToUser(username);
        //then
        verify(userRepository).findByUsername(username);
        verify(roleRepository).findByRoleName(RoleName.ROLE_STUDENT);
        verify(userRepository).save(user);
        assertTrue(user.getRoles().contains(studentRole));
    }

    @Test
    void addTeacherRoleToUserTest(){
        //given
        String username = user.getUsername();
        //when
        given(userRepository.findByUsername(username)).willReturn(user);
        given(roleRepository.findByRoleName(RoleName.ROLE_TEACHER)).willReturn(teacherRole);
        given(userRepository.save(user)).willReturn(user);
        User userGiven = underTest.addRoleTeacherToUser(username);
        //then
        verify(userRepository).findByUsername(username);
        verify(roleRepository).findByRoleName(RoleName.ROLE_TEACHER);
        verify(userRepository).save(user);
        assertTrue(user.getRoles().contains(teacherRole));
    }

    @Test
    void addManagerRoleToUserTest(){
        //given
        String username = user.getUsername();
        //when
        given(userRepository.findByUsername(username)).willReturn(user);
        given(roleRepository.findByRoleName(RoleName.ROLE_MANAGER)).willReturn(managerRole);
        given(userRepository.save(user)).willReturn(user);
        User userGiven = underTest.addRoleAndOptionsManagerToUser(username);
        //then
        verify(userRepository).findByUsername(username);
        verify(roleRepository).findByRoleName(RoleName.ROLE_MANAGER);
        verify(userRepository).save(user);
        assertTrue(user.getRoles().contains(managerRole));
    }
}