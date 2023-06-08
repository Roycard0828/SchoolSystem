package dev.SchoolSystem.Student.Service;

import dev.SchoolSystem.Student.DTO.StudentDTO;
import dev.SchoolSystem.Student.Entity.Student;
import dev.SchoolSystem.Student.Repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class StudentService {

    @Autowired
    private final StudentRepository studentRepository;

    // CRUD Functionality
    public Student createStudent(StudentDTO studentDTO){

        Student student = new Student(
                studentDTO.getAge(),
                studentDTO.getCourse(),
                studentDTO.getEmail(),
                studentDTO.getUser_id()
        );
        studentRepository.save(student);
        return student;
    }

    public List<Student> getStudentsByCourse(String course){
        return studentRepository.getStudentsByCourse(course);
    }

    public Student findStudentByIdentifier(String identifier){
        return studentRepository.findByIdentifier(identifier);
    }

    //Student functionality
}
