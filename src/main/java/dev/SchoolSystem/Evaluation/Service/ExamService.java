package dev.SchoolSystem.Evaluation.Service;

import dev.SchoolSystem.Auth.Entity.Option;
import dev.SchoolSystem.Classroom.Service.RecordService;
import dev.SchoolSystem.Evaluation.Entity.ExamAnswer;
import dev.SchoolSystem.Student.Entity.Student;
import dev.SchoolSystem.Classroom.Entity.Record;
import dev.SchoolSystem.Classroom.Repository.RecordRepository;
import dev.SchoolSystem.Evaluation.DTO.NewExamDTO;
import dev.SchoolSystem.Evaluation.Entity.Exam;
import dev.SchoolSystem.Evaluation.Repository.ExamRepository;
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
public class ExamService {

    @Autowired
    private final ExamRepository examRepository;
    @Autowired
    private final RecordService recordService;

    public Exam createExam(NewExamDTO examDTO){
        Record record = recordService.findRecordByClassCode(examDTO.getClass_code());
        Exam exam = new Exam(
                examDTO.getDescription(),
                examDTO.getContent(),
                examDTO.getAnswers(),
                record
        );
        for(Student student: record.getStudents()) {
            exam.getAnswers().add(new ExamAnswer(exam, student));
        }

        return examRepository.save(exam);
    }

    public Set<Exam> findAllExamsByRecordClassCode(String classCode){
        Record record = recordService.findRecordByClassCode(classCode);
        return examRepository.findAllByRecord(record);
    }

    public Exam findExamById(Long id){
        Optional<Exam> exam = examRepository.findById(id);
        if(exam.isEmpty()){
            log.error("Exam not found");
            throw new ResourceNotFoundException("Exam not found");
        }
        return exam.get();
    }

}
