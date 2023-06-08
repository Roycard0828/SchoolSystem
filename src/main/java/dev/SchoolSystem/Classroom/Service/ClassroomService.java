package dev.SchoolSystem.Classroom.Service;

import dev.SchoolSystem.Classroom.DTO.NewClassRoomDTO;
import dev.SchoolSystem.Classroom.DTO.NewRecordDTO;
import dev.SchoolSystem.Classroom.Entity.Classroom;
import dev.SchoolSystem.Classroom.Repository.ClassroomRepository;
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

    public Classroom createClass(NewClassRoomDTO newClassRoomDTO){
        Classroom classroom = new Classroom(
                newClassRoomDTO.getClassCode(),
                newClassRoomDTO.getSubject(),
                newClassRoomDTO.getTeacher()
        );
        classroomRepository.save(classroom);
        //Automatically create a record for this classroom
        recordService.createRecord(new NewRecordDTO(classroom));
        return classroom;
    }

    public Optional<Classroom> getClassroomByNumber(String classCode){
        return classroomRepository.findByClassCode(classCode);
    }

    //Classroom functionality
}
