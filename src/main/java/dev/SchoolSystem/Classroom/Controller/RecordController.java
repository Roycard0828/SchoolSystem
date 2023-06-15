package dev.SchoolSystem.Classroom.Controller;

import dev.SchoolSystem.Classroom.DTO.AddStudentDTO;
import dev.SchoolSystem.Classroom.Entity.Record;
import dev.SchoolSystem.Classroom.Service.RecordService;
import dev.SchoolSystem.Evaluation.Entity.Activity;
import dev.SchoolSystem.Evaluation.Entity.Exam;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/classroom/record")
@CrossOrigin
@RequiredArgsConstructor
public class RecordController {

    @Autowired
    private final RecordService recordService;

    @GetMapping("/{classCode}")
    public ResponseEntity<?> getRecordByClassCode(@PathVariable("classCode") String classCode){
        Record record = recordService.findRecordByClassCode(classCode);
        return new ResponseEntity<>(record, HttpStatus.OK);
    }

    @GetMapping("/{classCode}/activities")
    public ResponseEntity<Set<Activity>> getActivitiesByRecord(@PathVariable("classCode") String classCode){
        Set<Activity> activities = recordService.getAllActivitiesByRecordClassCode(classCode);
        return new ResponseEntity<>(activities, HttpStatus.OK);
    }

    @GetMapping("/{classCode}/exams")
    public ResponseEntity<Set<Exam>> getExamsByRecord(@PathVariable("classCode") String classCode){
        Set<Exam> exams = recordService.getAllExamsByRecordClassCode(classCode);
        return new ResponseEntity<>(exams, HttpStatus.OK);
    }

    @PatchMapping("/add-student")
    public ResponseEntity<?> addStudentToRecord(@Valid @RequestBody AddStudentDTO addStudentDTO){
        recordService.addStudentToRecord(addStudentDTO.getStudentIdentifier(),
                                        addStudentDTO.getClassCode());
        return new ResponseEntity<>("Student added", HttpStatus.OK);
    }

}
