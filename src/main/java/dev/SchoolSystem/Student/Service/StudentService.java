package dev.SchoolSystem.Student.Service;

import dev.SchoolSystem.Auth.Entity.User;
import dev.SchoolSystem.Auth.Service.RoleService;
import dev.SchoolSystem.Student.DTO.ProfileStudentDTO;
import dev.SchoolSystem.Student.DTO.StudentDTO;
import dev.SchoolSystem.Student.Entity.Student;
import dev.SchoolSystem.Student.Repository.StudentRepository;
import dev.SchoolSystem.Util.Exceptions.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@Slf4j
@AllArgsConstructor
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private RoleService roleService;

    // CRUD Functionality
    public Student createStudent(StudentDTO studentDTO, User user){

        Student student = new Student(
                studentDTO.getIdentifier(),
                studentDTO.getAge(),
                studentDTO.getCourse(),
                studentDTO.getEmail(),
                user
        );
        //Update the role of the user.
        roleService.addRoleAndOptionsStudentToUser(studentDTO.getUsername());

        studentRepository.save(student);
        return student;
    }

    public List<Student> getStudentsByCourse(String course){
        return studentRepository.getStudentsByCourse(course);
    }

    public Student findStudentByIdentifier(String identifier){
        Optional<Student> student = studentRepository.findByIdentifier(identifier);
        if(student.isEmpty()){
            log.error("Student not found");
            throw new ResourceNotFoundException("Student not found");
        }
        return student.get();
    }

    public ProfileStudentDTO getProfileStudent(Student student){
        ProfileStudentDTO profile = new ProfileStudentDTO(
                student.getIdentifier(),
                student.getUser_id().getName(),
                student.getUser_id().getLast_name(),
                student.getUser_id().getUsername(),
                student.getAge(),
                student.getCourse(),
                student.getEmail()
        );
        return profile;
    }
}
