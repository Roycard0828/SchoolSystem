package dev.SchoolSystem.Evaluation.Controller;

import dev.SchoolSystem.Evaluation.DTO.DeliverAnswerDTO;
import dev.SchoolSystem.Evaluation.DTO.ExamAnswerDTO;
import dev.SchoolSystem.Evaluation.Service.ExamAnswerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/classroom/record/exam/exam-answer")
@CrossOrigin
@RequiredArgsConstructor
public class ExamAnswerController {

    @Autowired
    private ExamAnswerService examAnswerService;

    @GetMapping("/{examId}")
    public ResponseEntity<?> getAllAnswersByExam(@PathVariable("examId")Long examId){
        return new ResponseEntity<>(examAnswerService.findAllAnswersByExam(examId), HttpStatus.OK);
    }

    @PostMapping("/add-note")
    public ResponseEntity<?> addNoteToExamAnswer(@Valid @RequestBody ExamAnswerDTO examAnswerDTO){
        return new ResponseEntity<>(examAnswerService.addNoteToExamByTeacher(examAnswerDTO), HttpStatus.OK);
    }

    @PostMapping("/get-answer")
    public ResponseEntity<?> getStudentExamAnswer(@Valid @RequestBody DeliverAnswerDTO answerDTO){
        return new ResponseEntity<>(
                examAnswerService.getExamAnswerByStudent(answerDTO.getExamId(), answerDTO.getStudentIdentifier()),
                HttpStatus.OK);
    }

    @PostMapping("/add-answer")
    public ResponseEntity<?> addAnswerToExam(@Valid @RequestBody DeliverAnswerDTO answerDTO){
        return new ResponseEntity<>(examAnswerService.addContentToExamAnswerByStudent(answerDTO),
                HttpStatus.OK);
    }

}

