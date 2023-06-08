package dev.SchoolSystem.Evaluation.Service;

import dev.SchoolSystem.Evaluation.Entity.ExamAnswer;
import dev.SchoolSystem.Student.Entity.Student;
import dev.SchoolSystem.Classroom.Entity.Record;
import dev.SchoolSystem.Classroom.Repository.RecordRepository;
import dev.SchoolSystem.Evaluation.DTO.NewExamDTO;
import dev.SchoolSystem.Evaluation.Entity.Exam;
import dev.SchoolSystem.Evaluation.Repository.ExamRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
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
    private final RecordRepository recordRepository;

    public Exam createExam(NewExamDTO examDTO){
        Optional<Record> record = recordRepository.findRecordByClassCode(examDTO.getClassCode());
        Exam exam = new Exam(
                examDTO.getDescription(),
                examDTO.getContent(),
                examDTO.getAnswers(),
                record.get()
        );
        for(Student student: record.get().getStudents()) {
            exam.getAnswers().add(new ExamAnswer(exam, student));
        }

        return examRepository.save(exam);
    }

    public Set<Exam> findAllExamsByRecordClassCode(String classCode) throws Exception {
        Optional<Record> record = recordRepository.findRecordByClassCode(classCode);
        if(record.isEmpty()){
            log.error("Exam not found");
            throw new Exception("Exam not found in the database");
        }
        return examRepository.findAllByRecord(record.get());
    }

    public Optional<Exam> findExamById(Long id){return examRepository.findById(id);}

}
