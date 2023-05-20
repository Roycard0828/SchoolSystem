package dev.SchoolSystem.Auth.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.SchoolSystem.Auth.DTO.NewUserDTO;
import dev.SchoolSystem.Auth.Entity.User;
import dev.SchoolSystem.Auth.Service.RoleService;
import dev.SchoolSystem.Auth.Service.UserService;
import dev.SchoolSystem.Config.JwtProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import javax.servlet.http.HttpServletRequest;

import java.util.ArrayList;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(AuthController.class)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private HttpServletRequest request;
    @MockBean
    private UserService userService;
    @MockBean
    private RoleService roleService;
    @MockBean
    private JwtProvider jwtProvider;

    private User user;
    private org.springframework.security.core.userdetails.User authU;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        user = new User("name", "last_name", "username", "password");
        authU = new org.springframework.security.core.userdetails.User(
                "username", "password", new ArrayList<>());
    }

    @Test
    void registerUserTest() throws Exception {
        NewUserDTO newUser = new NewUserDTO("name", "last_name", "username");
        when(userService.registerUser(newUser)).thenReturn(user);
        String jsonString = objectMapper.writeValueAsString(newUser);
        mockMvc.perform(post("/auth/user/register")
                        .contentType("application/json")
                        .content(jsonString))
                        .andExpect(status().is(201));
    }

    @Test
    void registerInvalidUserTest() throws Exception {
        //Sen user without username
        NewUserDTO newUser = new NewUserDTO("name", "last_name", "");
        when(userService.registerUser(newUser)).thenReturn(user);
        String jsonString = objectMapper.writeValueAsString(newUser);
        mockMvc.perform(post("/auth/user/register")
                        .contentType("application/json")
                        .content(jsonString))
                        .andExpect(status().isBadRequest());
    }

    @Test
    void addTeacherRoleToUserTest() throws Exception {
        when(roleService.addRoleTeacherToUser("username")).thenReturn(user);
        mockMvc.perform(post("/auth/role/addTeacherRole")
                        .contentType("application/json")
                        .content("username"))
                        .andExpect(status().isOk());
    }

    @Test
    void addTeacherRoleToNonExistsUserTest() throws Exception {
        when(roleService.addRoleTeacherToUser("no_username")).thenReturn(null);
        mockMvc.perform(post("/auth/role/addTeacherRole")
                        .contentType("application/json")
                        .content("no_username"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void addStudentRoleToUserTest() throws Exception {
        when(roleService.addRoleAndOptionsStudentToUser("username")).thenReturn(user);
        mockMvc.perform(post("/auth/role/addStudentRole")
                        .contentType("application/json")
                        .content("username"))
                .andExpect(status().isOk());
    }

    @Test
    void addStudentRoleToNonExistsUserTest() throws Exception {
        when(roleService.addRoleTeacherToUser("no_username")).thenReturn(null);
        mockMvc.perform(post("/auth/role/addStudentRole")
                        .contentType("application/json")
                        .content("no_username"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void respondBaseOnCorrectRefreshToken() throws Exception {
        String refreshToken = jwtProvider.generateRefreshToken(authU);
        mockMvc.perform(get("/auth/refreshToken")
                .header("AUTHORIZATION", "Bearer " + refreshToken))
                .andExpect(content().contentType("application/json"));
    }

    @Test
    void respondBaseOnIncorrectRefreshToken() throws Exception {
        String refreshToken = "";
        mockMvc.perform(get("/auth/refreshToken")
                        .header("AUTHORIZATION", "Bearer " + refreshToken))
                .andExpect(status().isForbidden())
                .andExpect(content().contentType("application/json"));
    }

    @Test
    void refreshTokenNotProvided() throws Exception {
        mockMvc.perform(get("/auth/refreshToken"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType("application/json"));
    }
}