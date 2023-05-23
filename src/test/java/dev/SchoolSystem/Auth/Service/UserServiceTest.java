package dev.SchoolSystem.Auth.Service;

import dev.SchoolSystem.Auth.DTO.NewUserDTO;
import dev.SchoolSystem.Auth.Entity.User;
import dev.SchoolSystem.Auth.Enums.RoleName;
import dev.SchoolSystem.Auth.Repository.RoleRepository;
import dev.SchoolSystem.Auth.Repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private RoleRepository roleRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @InjectMocks
    private UserService underTest;

    @BeforeEach
    void setUp() {
        underTest = new UserService(userRepository, roleRepository, passwordEncoder);
    }

    @AfterEach
    void tearDown() throws Exception{
    }

    @Test
    void registerUserTest(){
        //given
        NewUserDTO newUser = new NewUserDTO("test1", "last_name_test", "rg0838");
        //when
        underTest.registerUser(newUser);
        //then
        ArgumentCaptor<User> userArgumentCaptor =
                ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userArgumentCaptor.capture());
        verify(roleRepository).findByRoleName(RoleName.ROLE_USER);
        User capturedUser = userArgumentCaptor.getValue();

        assertInstanceOf(User.class, capturedUser);
    }

    @Test
    void checkTypeAndLengthGeneratePassword(){
        String password = underTest.generateFirstPassword();
        assertInstanceOf(String.class, password);
        assertEquals(10, password.length());
    }

    @Test
    void loadByUserNameTest(){
        //given
        String username = "username";
        User user = new User("name", "last_name", username, "password");
        given(userRepository.findByUsername(username)).willReturn(user);
        //when
        UserDetails userDetails = underTest.loadUserByUsername(username);
        //then
        verify(userRepository).findByUsername(username);
        assertInstanceOf(UserDetails.class, userDetails);
        assertNotNull(userDetails);
    }

    @Test
    void throwExceptionWhenLoadByUsernameFails(){
        //given
        String username = null;
        //when
        given(userRepository.findByUsername(username)).willReturn(null);
        //then
        assertThrowsExactly(UsernameNotFoundException.class, () -> underTest.loadUserByUsername(username));
    }

}