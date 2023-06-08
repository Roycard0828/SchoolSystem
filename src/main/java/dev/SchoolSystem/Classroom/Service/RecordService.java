package dev.SchoolSystem.Classroom.Service;

import dev.SchoolSystem.Classroom.DTO.NewRecordDTO;
import dev.SchoolSystem.Classroom.Entity.Record;
import dev.SchoolSystem.Classroom.Repository.RecordRepository;
import dev.SchoolSystem.Evaluation.Entity.Activity;
import dev.SchoolSystem.Evaluation.Entity.Exam;
import dev.SchoolSystem.Student.Entity.Student;
import dev.SchoolSystem.Student.Repository.StudentRepository;
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
public class RecordService {

    @Autowired
    private final RecordRepository recordRepository;
    @Autowired
    private final StudentRepository studentRepository;

    public Record createRecord(NewRecordDTO recordDTO){

        Record record = new Record(
                recordDTO.getClassroom(),
                recordDTO.getStudents()
        );
        return recordRepository.save(record);
    }

    public Optional<Record> findRecordByClassCode(String classCode) throws Exception {
        Optional<Record> record = recordRepository.findRecordByClassCode(classCode);
        if(record.isEmpty()){
            log.error("Record not found");
            throw new Exception("Record not found");
        }
        return record;
    }

    public Set<Activity> getAllActivitiesByRecordClassCode(String classCode){
        Optional<Record> record = recordRepository.findRecordByClassCode(classCode);
        Set<Activity> listActivities = null;
        if (record.isPresent()){
            listActivities = record.get().getActivities();
        }
        return listActivities;
    }

    public Set<Exam> getAllExamsByRecordClassCode(String classCode){
        Optional<Record> record = recordRepository.findRecordByClassCode(classCode);
        Set<Exam> listExams = null;
        if(record.isPresent()){
            listExams = record.get().getExams();
        }
        return listExams;
    }

    public void addStudentToRecord(String studentIdentifier, String classCode) throws Exception {
        Student student = studentRepository.findByIdentifier(studentIdentifier);
        Optional<Record> record = recordRepository.findRecordByClassCode(classCode);
        if(student != null){
            if(record.isEmpty()){
                log.error("Record not found");
                throw new Exception("Record not found");
            }
            //Add student
            record.get().getStudents().add(student);
            log.info("Adding student {} to record", student.toString());
            recordRepository.save(record.get());
        }else{
            log.error("Student does not exist");
            throw new Exception("Student does not exist");
        }
    }
}
