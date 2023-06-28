package dev.SchoolSystem.Classroom.Controller;

import dev.SchoolSystem.Classroom.DTO.AddStudentDTO;
import dev.SchoolSystem.Classroom.DTO.RecordResponse;
import dev.SchoolSystem.Classroom.Entity.Record;
import dev.SchoolSystem.Classroom.Service.RecordService;
import dev.SchoolSystem.Evaluation.DTO.Activity.ActivityDTO;
import dev.SchoolSystem.Evaluation.DTO.Exam.ExamDTO;
import dev.SchoolSystem.Evaluation.Entity.Activity;
import dev.SchoolSystem.Evaluation.Entity.Exam;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Set;

@RestController
@RequestMapping("/classroom/record")
@CrossOrigin
@RequiredArgsConstructor
public class RecordController {

    @Autowired
    private final RecordService recordService;

    @GetMapping("/{classCode}")
    @PreAuthorize("hasRole('ROLE_MANAGER') or hasRole('ROLE_TEACHER')")
    public ResponseEntity<?> getRecordByClassCode(@PathVariable("classCode") String classCode){
        Record record = recordService.findRecordByClassCode(classCode);
        RecordResponse recordResponse = new RecordResponse(
                record.getId(),
                recordService.transformToProfileStudent(record.getStudents())
        );
        return new ResponseEntity<>(recordResponse, HttpStatus.OK);
    }

    @GetMapping("/{classCode}/activities")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<?> getActivitiesByRecord(@PathVariable("classCode") String classCode){
        Set<Activity> activities = recordService.getAllActivitiesByRecordClassCode(classCode);
        Set<ActivityDTO> activityDTOS = recordService.transformToActivityDTO(activities);
        return new ResponseEntity<>(activityDTOS, HttpStatus.OK);
    }

    @GetMapping("/{classCode}/exams")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<?> getExamsByRecord(@PathVariable("classCode") String classCode){
        Set<Exam> exams = recordService.getAllExamsByRecordClassCode(classCode);
        Set<ExamDTO> examDTOS = recordService.transformToExamsDTO(exams);
        return new ResponseEntity<>(examDTOS, HttpStatus.OK);
    }

    @PatchMapping("/add-student")
    @PreAuthorize("hasRole('ROLE_MANAGER') or hasRole('ROLE_TEACHER')")
    public ResponseEntity<?> addStudentToRecord(@Valid @RequestBody AddStudentDTO addStudentDTO){
        recordService.addStudentToRecord(addStudentDTO.getStudent_identifier(),
                                        addStudentDTO.getClass_code());
        return new ResponseEntity<>("Student added", HttpStatus.OK);
    }

}
