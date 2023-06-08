package dev.SchoolSystem.Evaluation.Service;

import dev.SchoolSystem.Evaluation.DTO.ExamAnswerDTO;
import dev.SchoolSystem.Evaluation.Entity.Activity;
import dev.SchoolSystem.Evaluation.Entity.Exam;
import dev.SchoolSystem.Evaluation.Entity.ExamAnswer;
import dev.SchoolSystem.Evaluation.Repository.ExamAnswerRepository;
import dev.SchoolSystem.Student.Entity.Student;
import dev.SchoolSystem.Student.Service.StudentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
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
        Optional<Exam> exam = examService.findExamById(answerDTO.getExamId());
        return examAnswerRepository.save(new ExamAnswer(exam.get(), student));
    }

    //Teacher's functionality

    public Set<ExamAnswer> findAllAnswersByExam(Long examId) throws Exception {
        Optional<Exam> exam = examService.findExamById(examId);
        if(exam.isEmpty()){
            log.error("Exam not found");
            throw new Exception("Exam not found");
        }
        return examAnswerRepository.findAllByExam(exam.get());
    }

    public ExamAnswer addNoteToExamByTeacher(ExamAnswerDTO answerDTO, double note) throws Exception {
        Student student = studentService.findStudentByIdentifier(answerDTO.getStudentIdentifier());
        Optional<Exam> exam = examService.findExamById(answerDTO.getExamId());
        ExamAnswer answer = null;

        if(exam.isPresent()){
            answer = examAnswerRepository.findByExamAndStudent(
                    exam.get(),
                    student
            );
            answer.setNote(note);
        }else{
            log.error("Exam not found");
            throw new Exception("Exam not found");
        }

        return examAnswerRepository.save(answer);
    }

    //Student's functionality

    public ExamAnswer getExamAnswerByStudent(Long examId, String studentIdentifier){
        Optional<Exam> exam = examService.findExamById(examId);
        Student student = studentService.findStudentByIdentifier(studentIdentifier);

        return examAnswerRepository.findByExamAndStudent(exam.get(), student);
    }

    public ExamAnswer addContentToExamAnswerByStudent(ExamAnswerDTO answerDTO, String content, Date deliveryDate) throws Exception {
        Student student = studentService.findStudentByIdentifier(answerDTO.getStudentIdentifier());
        Optional<Exam> exam = examService.findExamById(answerDTO.getExamId());
        ExamAnswer answer = null;

        if(exam.isPresent()){
            answer = examAnswerRepository.findByExamAndStudent(
              exam.get(),
              student
            );
            answer.setContent(content);
        }else{
            log.error("ExamAnswer not found");
            throw new Exception("ExamAnswer not found");
        }
        return examAnswerRepository.save(answer);
    }

}
