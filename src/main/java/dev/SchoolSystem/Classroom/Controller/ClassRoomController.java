package dev.SchoolSystem.Classroom.Controller;

import dev.SchoolSystem.Classroom.DTO.ClassRoomDTO;
import dev.SchoolSystem.Classroom.Entity.Classroom;
import dev.SchoolSystem.Classroom.Service.ClassroomService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/classroom")
@CrossOrigin
@RequiredArgsConstructor
public class ClassRoomController {

    @Autowired
    private ClassroomService classroomService;

    @PostMapping()
    @PreAuthorize("hasRole('ROLE_MANAGER') or hasRole('ROLE_USER')")
    public ResponseEntity<String> createClassRoom(@Valid @RequestBody ClassRoomDTO classRoomDTO){
        classroomService.createClass(classRoomDTO);
        return new ResponseEntity<>("Classroom created", HttpStatus.CREATED);
    }
    @GetMapping("/{classCode}")
    @PreAuthorize("hasRole('ROLE_MANAGER') or hasRole('ROLE_USER')")
    public ResponseEntity<?> getClassroom(@PathVariable("classCode") String classCode){
        Classroom classroom = classroomService.getClassroomByClassCode(classCode);
        return new ResponseEntity<>(classroom, HttpStatus.OK);
    }

}
