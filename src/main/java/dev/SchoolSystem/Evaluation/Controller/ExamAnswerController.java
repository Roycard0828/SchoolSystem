package dev.SchoolSystem.Evaluation.Controller;

import dev.SchoolSystem.Evaluation.DTO.Exam.ExamAnswerDTO;
import dev.SchoolSystem.Evaluation.Entity.ExamAnswer;
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

    @PostMapping("/add-note")
    public ResponseEntity<?> addNoteToExamAnswer(@Valid @RequestBody ExamAnswerDTO examAnswerDTO){
        return new ResponseEntity<>(examAnswerService.addNoteToExamByTeacher(examAnswerDTO), HttpStatus.OK);
    }

    @PostMapping("/get-answer")
    public ResponseEntity<?> getStudentExamAnswer(@Valid @RequestBody ExamAnswerDTO answerDTO){
        ExamAnswer examAnswer = examAnswerService.getExamAnswerByStudent(
                answerDTO.getExam_id(),
                answerDTO.getStudent_identifier());
        ExamAnswerDTO examAnswerResponse = examAnswerService.transformToExamAnswer(examAnswer);

        return new ResponseEntity<>(examAnswerResponse, HttpStatus.OK);
    }

    @PostMapping("/add-answer")
    public ResponseEntity<?> addAnswerToExam(@RequestBody ExamAnswerDTO answerDTO){
        return new ResponseEntity<>(examAnswerService.addContentToExamAnswerByStudent(answerDTO),
                HttpStatus.OK);
    }

}

