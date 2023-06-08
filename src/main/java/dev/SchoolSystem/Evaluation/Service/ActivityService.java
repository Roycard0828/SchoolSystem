package dev.SchoolSystem.Evaluation.Service;

import dev.SchoolSystem.Classroom.Entity.Record;
import dev.SchoolSystem.Classroom.Repository.RecordRepository;
import dev.SchoolSystem.Evaluation.DTO.NewActivityDTO;
import dev.SchoolSystem.Evaluation.Entity.ActDelivery;
import dev.SchoolSystem.Evaluation.Entity.Activity;
import dev.SchoolSystem.Evaluation.Repository.ActivityRepository;
import dev.SchoolSystem.Student.Entity.Student;
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
public class ActivityService {

    @Autowired
    private final ActivityRepository activityRepository;
    @Autowired
    private final RecordRepository recordRepository;

    public Activity createActivity(NewActivityDTO activityDTO){
        Activity activity = new Activity(
                activityDTO.getDescription(),
                activityDTO.getDeliveries(),
                activityDTO.getRecord()
        );
        //Create automatically empty deliverables for all students of a record.
        if(activity.getRecord().getStudents().size() > 0){
            for(Student student: activity.getRecord().getStudents()) {
                activity.getDeliveries().add(new ActDelivery(student, activity));
            }
        }
        return activityRepository.save(activity);
    }

    public Set<Activity> findAllActivitiesByRecordClassCode(String classCode) throws Exception {
        Optional<Record> record = recordRepository.findRecordByClassCode(classCode);
        if (record.isEmpty()){
            log.error("Record not found");
            throw new Exception("Record not found in the database");
        }
        return activityRepository.findAllByRecord(record.get());
    }

    public Optional<Activity> findActivityById(Long id){
        return activityRepository.findById(id);
    }

}
