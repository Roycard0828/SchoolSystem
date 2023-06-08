package dev.SchoolSystem.Evaluation.Service;


import dev.SchoolSystem.Evaluation.DTO.ActDeliveryDTO;
import dev.SchoolSystem.Evaluation.Entity.ActDelivery;
import dev.SchoolSystem.Evaluation.Entity.Activity;
import dev.SchoolSystem.Evaluation.Repository.ActDeliveryRepository;
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
public class ActDeliveryService {

    @Autowired
    private final ActDeliveryRepository actDeliveryRepository;
    @Autowired
    private final ActivityService activityService;
    @Autowired
    private final StudentService studentService;

    public ActDelivery createActDelivery(ActDeliveryDTO deliveryDTO){
        Student student = studentService.findStudentByIdentifier(deliveryDTO.getStudentIdentifier());
        Optional<Activity> activity = activityService.findActivityById(deliveryDTO.getActivityId());
        return actDeliveryRepository.save(new ActDelivery(student, activity.get()));
    }

    //Teacher's functionalities
    public Set<ActDelivery> findDeliveriesByActivity(Long activityId) throws Exception {
        Optional<Activity> activity = activityService.findActivityById(activityId);
        if (activity.isEmpty()){
            log.error("Activity not found");
            throw new Exception("Activity not found");
        }
        return actDeliveryRepository.findByActivity(activity.get());
    }

    public ActDelivery addNoteToDeliveryByTeacher(ActDeliveryDTO deliveryDTO, double note) throws Exception {
        Student student = studentService.findStudentByIdentifier(deliveryDTO.getStudentIdentifier());
        Optional<Activity> activity = activityService.findActivityById(deliveryDTO.getActivityId());
        ActDelivery actDelivery = null;

        if (activity.isPresent()){
            actDelivery = actDeliveryRepository.findByActivityAndStudent(
                    activity.get(),
                    student
            );
            actDelivery.setNote(note);
        }else{
            log.error("Activity not found");
            throw new Exception("Activity not found");
        }

        return actDeliveryRepository.save(actDelivery);
    }

    //Student's functionality

    public ActDelivery getActDeliveryByStudent(Long activityId, String studentIdentifier){
        Optional<Activity> activity = activityService.findActivityById(activityId);
        Student student = studentService.findStudentByIdentifier(studentIdentifier);

        return actDeliveryRepository.findByActivityAndStudent(activity.get(), student);
    }

    public ActDelivery addContentToDeliveryByStudent(ActDeliveryDTO deliveryDTO, String newContent, Date deliveryDate) throws Exception {
        Student student = studentService.findStudentByIdentifier(deliveryDTO.getStudentIdentifier());
        Optional<Activity> activity = activityService.findActivityById(deliveryDTO.getActivityId());

        ActDelivery actDelivery = null;

        if(activity.isPresent()){
            actDelivery = actDeliveryRepository.findByActivityAndStudent(
                    activity.get(),
                    student
            );
            actDelivery.setContent(newContent);
            actDelivery.setDeliveryDate(deliveryDate);
        }else{
            log.error("Activity not found");
            throw new Exception("Activity not found");
        }
        return actDeliveryRepository.save(actDelivery);
    }

}
