package dev.SchoolSystem.Evaluation.Service;

import dev.SchoolSystem.Auth.Entity.Option;
import dev.SchoolSystem.Evaluation.DTO.DeliverAnswerDTO;
import dev.SchoolSystem.Evaluation.DTO.ExamAnswerDTO;
import dev.SchoolSystem.Evaluation.Entity.Exam;
import dev.SchoolSystem.Evaluation.Entity.ExamAnswer;
import dev.SchoolSystem.Evaluation.Repository.ExamAnswerRepository;
import dev.SchoolSystem.Student.Entity.Student;
import dev.SchoolSystem.Student.Service.StudentService;
import dev.SchoolSystem.Util.Exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class ExamAnswerService {

    @Autowired
    private final ExamAnswerRepository examAnswerRepository;
    @Autowired
    private final ExamService examService;
    @Autowired
    private final StudentService studentService;

    public ExamAnswer createAnswer(ExamAnswerDTO answerDTO){
        Student student = studentService.findStudentByIdentifier(answerDTO.getStudentIdentifier());
        Exam exam = examService.findExamById(answerDTO.getExamId());
        return examAnswerRepository.save(new ExamAnswer(exam, student));
    }

    //Teacher's functionality

    public Set<ExamAnswer> findAllAnswersByExam(Long examId){
        Exam exam = examService.findExamById(examId);
        return examAnswerRepository.findAllByExam(exam);
    }

    public ExamAnswer addNoteToExamByTeacher(ExamAnswerDTO answerDTO){
        ExamAnswer answer = getExamAnswerByStudent(answerDTO.getExamId(), answerDTO.getStudentIdentifier());
        answer.setNote(answerDTO.getNote());
        return examAnswerRepository.save(answer);
    }

    //Student's functionality

    public ExamAnswer getExamAnswerByStudent(Long examId, String studentIdentifier){
        Exam exam = examService.findExamById(examId);
        Student student = studentService.findStudentByIdentifier(studentIdentifier);
        Optional<ExamAnswer> answer = examAnswerRepository.findByExamAndStudent(exam, student);
        if(answer.isEmpty()){
            log.error("Exam Answer not found");
            throw new ResourceNotFoundException(getClass().getSimpleName(), "Exam Answer not found");
        }
        return answer.get();
    }

    public ExamAnswer addContentToExamAnswerByStudent(DeliverAnswerDTO answerDTO){
        ExamAnswer answer = getExamAnswerByStudent(answerDTO.getExamId(), answerDTO.getStudentIdentifier());
        answer.setContent(answerDTO.getContent());
        return examAnswerRepository.save(answer);
    }

}
