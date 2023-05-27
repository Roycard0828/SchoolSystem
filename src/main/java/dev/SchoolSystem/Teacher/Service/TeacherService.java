package dev.SchoolSystem.Teacher.Service;

import dev.SchoolSystem.Teacher.DTO.TeacherDTO;
import dev.SchoolSystem.Teacher.Entity.Teacher;
import dev.SchoolSystem.Teacher.Repository.TeacherRepository;
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
public class TeacherService {

    @Autowired
    private TeacherRepository teacherRepository;

    //CRUD operations
    public Teacher createTeacher(TeacherDTO teacherDTO){

        Teacher teacher = new Teacher(
            teacherDTO.getAge(),
            teacherDTO.getProfessional_title(),
            teacherDTO.getEmail(),
            teacherDTO.getUser_id()
        );
        teacherRepository.save(teacher);
        return teacher;
    }

    public Teacher findTeacherByIdentifier(Long identifier){
        return teacherRepository.findByIdentifier(identifier);
    }

    public List<Teacher> getAllTeachers(){
        return teacherRepository.findAll();
    }

    //Teacher functionality

}
