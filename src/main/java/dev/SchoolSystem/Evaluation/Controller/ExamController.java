package dev.SchoolSystem.Evaluation.Controller;

import dev.SchoolSystem.Evaluation.DTO.NewExamDTO;
import dev.SchoolSystem.Evaluation.Service.ExamService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/classroom/record/exam")
@CrossOrigin
@RequiredArgsConstructor
public class ExamController {

    @Autowired
    private final ExamService examService;

    @PostMapping()
    @PreAuthorize("hasRole('ROLE_TEACHER')")
    public ResponseEntity<?> createExam(@Valid @RequestBody NewExamDTO examDTO){
        return new ResponseEntity<>(examService.createExam(examDTO), HttpStatus.CREATED);
    }

    @GetMapping("/{classCode}")
    @PreAuthorize("hasRole('ROLE_TEACHER')")
    public ResponseEntity<?> getAllExamsByRecord(@PathVariable("classCode")String classCode){
        return new ResponseEntity<>(examService.findAllExamsByRecordClassCode(classCode),
                HttpStatus.OK);
    }

}
