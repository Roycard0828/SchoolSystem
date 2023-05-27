package dev.SchoolSystem.Classroom.Service;

import dev.SchoolSystem.Classroom.DTO.ClassroomDTO;
import dev.SchoolSystem.Classroom.Entity.Classroom;
import dev.SchoolSystem.Classroom.Repository.ClassroomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class ClassroomService {

    @Autowired
    private final ClassroomRepository classroomRepository;

    public Classroom createClass(ClassroomDTO classroomDTO){
        Classroom classroom = new Classroom(
                classroomDTO.getNumber(),
                classroomDTO.getSubject(),
                classroomDTO.getTeacher(),
                classroomDTO.getStudents()
        );
        classroomRepository.save(classroom);
        return classroom;
    }

    public Classroom getClassroomByNumber(int number){
        return classroomRepository.findByNumber(number);
    }

    //Classroom functionality

}
