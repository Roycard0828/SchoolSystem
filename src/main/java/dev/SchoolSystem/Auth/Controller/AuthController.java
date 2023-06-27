package dev.SchoolSystem.Auth.Controller;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.SchoolSystem.Auth.DTO.NewUserDTO;
import dev.SchoolSystem.Auth.Entity.User;
import dev.SchoolSystem.Auth.Service.RoleService;
import dev.SchoolSystem.Auth.Service.UserService;
import dev.SchoolSystem.Config.JwtProvider;
import dev.SchoolSystem.Student.DTO.StudentDTO;
import dev.SchoolSystem.Student.Service.StudentService;
import dev.SchoolSystem.Teacher.DTO.TeacherDTO;
import dev.SchoolSystem.Teacher.Service.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static java.util.Arrays.stream;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/auth")
@CrossOrigin
@RequiredArgsConstructor
public class AuthController {

    @Autowired
    private final UserService userService;
    @Autowired
    private final RoleService roleService;
    @Autowired
    private final StudentService studentService;
    @Autowired
    private final TeacherService teacherService;
    @Autowired
    private final JwtProvider jwtProvider;

    @PostMapping("/user/register")
    public ResponseEntity<User> registerUser(@Valid @RequestBody NewUserDTO newUser){
        User user = userService.registerUser(newUser);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @GetMapping("/user/{username}")
    public ResponseEntity<User> getUser(@PathVariable("username") String username){
        User user = userService.getUser(username);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping("/role/addStudentRole")
    public ResponseEntity<String> addStudentRole(@Valid @RequestBody StudentDTO studentDTO) {
        User user = roleService.addRoleAndOptionsStudentToUser(studentDTO.getUsername());
        studentService.createStudent(studentDTO, user);
        return new ResponseEntity<>("Student role added to user", HttpStatus.OK);
    }

    @PostMapping("/role/addTeacherRole")
    public ResponseEntity<String> addTeacherRole(@Valid @RequestBody TeacherDTO teacherDTO){
        User user = roleService.addRoleAndOptionsTeacherToUser(teacherDTO.getUsername());
        teacherService.createTeacher(teacherDTO, user);
        return new ResponseEntity<>("Teacher role added to user", HttpStatus.OK);
    }

    @GetMapping("/refreshToken")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //Create a new token from the refresh token request.
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){
            try {
                String refreshToken = authorizationHeader.substring("Bearer ".length());
                DecodedJWT decodedJWT = jwtProvider.decodeToken(refreshToken);
                String username = decodedJWT.getSubject();
                User user = userService.getUser(username);
                String accessToken = jwtProvider.generateTokenFromRefreshToken(request, user);
                //Send the tokens in the body response.
                Map<String, String> tokens = new HashMap<>();
                tokens.put("access_token", accessToken);
                tokens.put("refresh_token", refreshToken);
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), tokens);
            }catch (Exception ex){
                response.setHeader("error", ex.getMessage());
                response.setStatus(FORBIDDEN.value());
                Map<String, String> error = new HashMap<>();
                error.put("error_message", ex.getMessage());
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), error);
            }
        }else{
            response.setHeader("error", "authorization not provided");
            response.setStatus(BAD_REQUEST.value());
            Map<String, String> error = new HashMap<>();
            error.put("error_message", "authorization not provided");
            response.setContentType(APPLICATION_JSON_VALUE);
            new ObjectMapper().writeValue(response.getOutputStream(), error);
        }
    }

}
