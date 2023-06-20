package dev.SchoolSystem.Evaluation.Service;


import dev.SchoolSystem.Evaluation.DTO.ActDeliveryDTO;
import dev.SchoolSystem.Evaluation.DTO.DeliverDeliveryDTO;
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
        Activity activity = activityService.findActivityById(deliveryDTO.getActivityId());
        return actDeliveryRepository.save(new ActDelivery(student, activity));
    }

    //Teacher's functionalities
    public Set<ActDelivery> findDeliveriesByActivity(Long activityId){
        Activity activity = activityService.findActivityById(activityId);
        return actDeliveryRepository.findByActivity(activity);
    }

    public ActDelivery addNoteToDeliveryByTeacher(ActDeliveryDTO deliveryDTO){
        Student student = studentService.findStudentByIdentifier(deliveryDTO.getStudentIdentifier());
        Activity activity = activityService.findActivityById(deliveryDTO.getActivityId());
        ActDelivery actDelivery = actDeliveryRepository.findByActivityAndStudent(
                    activity,
                    student);
        actDelivery.setNote(deliveryDTO.getNote());
        return actDeliveryRepository.save(actDelivery);
    }

    //Student's functionality

    public ActDelivery getActDeliveryByStudent(Long activityId, String studentIdentifier){
        Activity activity = activityService.findActivityById(activityId);
        Student student = studentService.findStudentByIdentifier(studentIdentifier);

        return actDeliveryRepository.findByActivityAndStudent(activity, student);
    }

    public ActDelivery addContentToDeliveryByStudent(DeliverDeliveryDTO deliveryDTO){
        Student student = studentService.findStudentByIdentifier(deliveryDTO.getStudentIdentifier());
        Activity activity = activityService.findActivityById(deliveryDTO.getActivityId());

        ActDelivery actDelivery = actDeliveryRepository.findByActivityAndStudent(
                    activity,
                    student);
        actDelivery.setContent(deliveryDTO.getContent());
        actDelivery.setDeliveryDate(deliveryDTO.getDeliveryDate());

        return actDeliveryRepository.save(actDelivery);
    }

}
