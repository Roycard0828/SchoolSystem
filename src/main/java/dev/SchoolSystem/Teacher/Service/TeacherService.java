package dev.SchoolSystem.Teacher.Service;

import dev.SchoolSystem.Auth.Service.RoleService;
import dev.SchoolSystem.Teacher.DTO.TeacherDTO;
import dev.SchoolSystem.Teacher.Entity.Teacher;
import dev.SchoolSystem.Teacher.Repository.TeacherRepository;
import dev.SchoolSystem.Util.Exceptions.ResourceNotFoundException;
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
    @Autowired
    private RoleService roleService;

    //CRUD operations
    public Teacher createTeacher(TeacherDTO teacherDTO){

        Teacher teacher = new Teacher(teacherDTO.getIdentifier(),
            teacherDTO.getAge(),
            teacherDTO.getProfessional_title(),
            teacherDTO.getEmail(),
            teacherDTO.getUser_id()
        );

        //Update the role of the user
        roleService.addRoleAndOptionsTeacherToUser(teacherDTO.getUser_id().getUsername());

        teacherRepository.save(teacher);
        return teacher;
    }

    public Teacher findTeacherByIdentifier(String identifier){
        Teacher teacher = teacherRepository.findByIdentifier(identifier);
        if(teacher == null){
            throw  new ResourceNotFoundException(getClass().getSimpleName(), "Teacher not found");
        }
        return teacher;
    }

    public List<Teacher> getAllTeachers(){
        return teacherRepository.findAll();
    }

    //Teacher functionality

}
