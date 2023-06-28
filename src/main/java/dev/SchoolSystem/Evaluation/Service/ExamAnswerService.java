package dev.SchoolSystem.Evaluation.Service;

import dev.SchoolSystem.Evaluation.DTO.Exam.ExamAnswerDTO;
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

    public ExamAnswer createAnswer(dev.SchoolSystem.Evaluation.DTO.Exam.ExamAnswerDTO answerDTO){
        Student student = studentService.findStudentByIdentifier(answerDTO.getStudent_identifier());
        Exam exam = examService.findExamById(answerDTO.getExam_id());
        return examAnswerRepository.save(new ExamAnswer(exam, student));
    }

    //Teacher's functionality

    public Set<ExamAnswer> findAllAnswersByExam(Long examId){
        Exam exam = examService.findExamById(examId);
        return examAnswerRepository.findAllByExam(exam);
    }

    public ExamAnswer addNoteToExamByTeacher(dev.SchoolSystem.Evaluation.DTO.Exam.ExamAnswerDTO answerDTO){
        ExamAnswer answer = getExamAnswerByStudent(answerDTO.getExam_id(), answerDTO.getStudent_identifier());
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

    public ExamAnswer addContentToExamAnswerByStudent(ExamAnswerDTO answerDTO){
        ExamAnswer answer = getExamAnswerByStudent(answerDTO.getExam_id(), answerDTO.getStudent_identifier());
        answer.setContent(answerDTO.getContent());
        return examAnswerRepository.save(answer);
    }

    //Extra methods.
    public ExamAnswerDTO transformToExamAnswer(ExamAnswer answer){
        return new ExamAnswerDTO(
                answer.getStudent().getIdentifier(),
                answer.getExam().getId(),
                answer.getNote(),
                answer.getContent(),
                studentService.getProfileStudent(answer.getStudent())
        );
    }

}
