package dev.SchoolSystem.Evaluation.Controller;

import dev.SchoolSystem.Evaluation.DTO.Exam.ExamAnswerDTO;
import dev.SchoolSystem.Evaluation.DTO.Exam.ExamDTO;
import dev.SchoolSystem.Evaluation.Entity.Exam;
import dev.SchoolSystem.Evaluation.Entity.ExamAnswer;
import dev.SchoolSystem.Evaluation.Service.ExamAnswerService;
import dev.SchoolSystem.Evaluation.Service.ExamService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/classroom/record/exam")
@CrossOrigin
@RequiredArgsConstructor
public class ExamController {

    @Autowired
    private final ExamService examService;
    @Autowired
    private final ExamAnswerService answerService;

    @PostMapping()
    @PreAuthorize("hasRole('ROLE_TEACHER')")
    public ResponseEntity<?> createExam(@Valid @RequestBody ExamDTO examDTO){
        return new ResponseEntity<>(examService.createExam(examDTO), HttpStatus.CREATED);
    }

    @GetMapping("/{examId}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<?> getExam(@PathVariable("examId") int examId){
        Exam exam = examService.findExamById((long) examId);
        ExamDTO examResponse;
        Set<ExamAnswerDTO> answerResponse = new HashSet<>();

        for (ExamAnswer answer: exam.getAnswers()) {
            answerResponse.add(answerService.transformToExamAnswer(answer));
        }

        examResponse = new ExamDTO(
                (long) examId,
                exam.getDescription(),
                exam.getContent(),
                exam.getRecord().getClassroom().getClassCode(),
                answerResponse
        );
        return new ResponseEntity<>(examResponse, HttpStatus.OK);
    }
}
