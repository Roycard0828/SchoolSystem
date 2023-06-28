package dev.SchoolSystem;

import dev.SchoolSystem.Auth.DTO.NewOptionDTO;
import dev.SchoolSystem.Auth.DTO.NewUserDTO;
import dev.SchoolSystem.Auth.Entity.Role;
import dev.SchoolSystem.Auth.DTO.RoleNameDTO;
import dev.SchoolSystem.Auth.Entity.User;
import dev.SchoolSystem.Auth.Enums.RoleName;
import dev.SchoolSystem.Auth.Repository.RoleRepository;
import dev.SchoolSystem.Auth.Service.OptionService;
import dev.SchoolSystem.Auth.Service.RoleService;
import dev.SchoolSystem.Auth.Service.UserService;
import dev.SchoolSystem.Classroom.DTO.ClassRoomDTO;
import dev.SchoolSystem.Classroom.Entity.Record;
import dev.SchoolSystem.Classroom.Service.ClassroomService;
import dev.SchoolSystem.Classroom.Service.RecordService;
import dev.SchoolSystem.Subejct.DTO.SubjectDTO;
import dev.SchoolSystem.Subejct.Service.SubjectService;
import dev.SchoolSystem.Teacher.DTO.TeacherDTO;
import dev.SchoolSystem.Teacher.Service.TeacherService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class SchoolSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(SchoolSystemApplication.class, args);
	}

	@Bean
	PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}

	@Bean
	UserDetailsService userDetailsService(){
		return new UserDetailsService() {
			@Override
			public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
				return null;
			}
		};
	}

	@Bean
	public CommandLineRunner commandLineRunner(){
		return args -> {
		};
	}

}
