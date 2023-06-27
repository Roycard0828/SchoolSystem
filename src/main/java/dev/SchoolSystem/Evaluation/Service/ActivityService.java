package dev.SchoolSystem.Evaluation.Service;

import dev.SchoolSystem.Classroom.Entity.Record;
import dev.SchoolSystem.Classroom.Service.RecordService;
import dev.SchoolSystem.Evaluation.DTO.Activity.ActivityDTO;
import dev.SchoolSystem.Evaluation.Entity.ActDelivery;
import dev.SchoolSystem.Evaluation.Entity.Activity;
import dev.SchoolSystem.Evaluation.Repository.ActivityRepository;
import dev.SchoolSystem.Student.Entity.Student;
import dev.SchoolSystem.Util.Exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Optional;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class ActivityService {

    @Autowired
    private final ActivityRepository activityRepository;
    @Autowired
    private final RecordService recordService;

    public Activity createActivity(ActivityDTO activityDTO){
        Record record = recordService.findRecordByClassCode(activityDTO.getRecord_class_code());
        Activity activity = new Activity(
                activityDTO.getDescription(),
                new HashSet<>(),
                record
        );
        //Create automatically empty deliverables for all students of a record.
        if(activity.getRecord().getStudents().size() > 0){
            for(Student student: activity.getRecord().getStudents()) {
                activity.getDeliveries().add(new ActDelivery(student, activity));
            }
        }
        return activityRepository.save(activity);
    }

    public Activity findActivityById(Long id){
        Optional<Activity> activity = activityRepository.findById(id);
        if(activity.isEmpty()){
            log.error("Activity not found");
            throw new ResourceNotFoundException(getClass().getSimpleName(), "Activity not found");
        }
        return activity.get();
    }



}
