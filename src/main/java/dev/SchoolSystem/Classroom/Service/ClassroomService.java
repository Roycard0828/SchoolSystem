package dev.SchoolSystem.Classroom.Service;

import dev.SchoolSystem.Classroom.DTO.ClassRoomDTO;
import dev.SchoolSystem.Classroom.DTO.RecordDTO;
import dev.SchoolSystem.Classroom.Entity.Classroom;
import dev.SchoolSystem.Classroom.Repository.ClassroomRepository;
import dev.SchoolSystem.Subejct.Entity.Subject;
import dev.SchoolSystem.Subejct.Service.SubjectService;
import dev.SchoolSystem.Teacher.Entity.Teacher;
import dev.SchoolSystem.Teacher.Service.TeacherService;
import dev.SchoolSystem.Util.Exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class ClassroomService {

    @Autowired
    private final ClassroomRepository classroomRepository;
    @Autowired
    private final RecordService recordService;
    @Autowired
    private final SubjectService subjectService;
    @Autowired
    private final TeacherService teacherService;

    public Classroom createClass(ClassRoomDTO classRoomDTO){
        Subject subject = subjectService.findByIdentifier(classRoomDTO.getSubjectIdentifier());
        Teacher teacher = teacherService.findTeacherByIdentifier(classRoomDTO.getTeacherIdentifier());
        Classroom classroom = new Classroom(
                classRoomDTO.getClassCode(),
                subject,
                teacher
        );
        classroomRepository.save(classroom);
        //Automatically create a record for this classroom
        recordService.createRecord(new RecordDTO(classroom));
        return classroom;
    }

    public Classroom getClassroomByClassCode(String classCode){
        Optional<Classroom> clasroom = classroomRepository.findByClassCode(classCode);
        if(clasroom.isEmpty()){
            throw new ResourceNotFoundException(getClass().getSimpleName(), "Classroom not found");
        }
        return clasroom.get();
    }

    //Classroom functionality
}
