package dev.SchoolSystem.Evaluation.Service;


import dev.SchoolSystem.Auth.Entity.Option;
import dev.SchoolSystem.Evaluation.DTO.Activity.ActDeliveryDTO;
import dev.SchoolSystem.Evaluation.Entity.ActDelivery;
import dev.SchoolSystem.Evaluation.Entity.Activity;
import dev.SchoolSystem.Evaluation.Repository.ActDeliveryRepository;
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
public class ActDeliveryService {

    @Autowired
    private final ActDeliveryRepository actDeliveryRepository;
    @Autowired
    private final ActivityService activityService;
    @Autowired
    private final StudentService studentService;

    public ActDelivery createActDelivery(ActDeliveryDTO deliveryDTO){
        Student student = studentService.findStudentByIdentifier(deliveryDTO.getStudent_identifier());
        Activity activity = activityService.findActivityById(deliveryDTO.getActivity_id());
        return actDeliveryRepository.save(new ActDelivery(student, activity));
    }

    //Teacher's functionalities
    public Set<ActDelivery> findDeliveriesByActivity(Long activityId){
        Activity activity = activityService.findActivityById(activityId);
        return actDeliveryRepository.findByActivity(activity);
    }

    public ActDelivery addNoteToDeliveryByTeacher(ActDeliveryDTO deliveryDTO){
        Student student = studentService.findStudentByIdentifier(deliveryDTO.getStudent_identifier());
        Activity activity = activityService.findActivityById(deliveryDTO.getActivity_id());
        Optional<ActDelivery> actDelivery = actDeliveryRepository.findByActivityAndStudent(activity, student);
        if(actDelivery.isEmpty()){
            log.error("This student doesn't have this activity");
            throw new ResourceNotFoundException("Not found activity for this student.");
        }
        actDelivery.get().setNote(deliveryDTO.getNote());
        actDeliveryRepository.save(actDelivery.get());
        return actDelivery.get();
    }

    //Student's functionality

    public ActDelivery getActDeliveryByStudent(ActDeliveryDTO deliveryDTO){
        Activity activity = activityService.findActivityById(deliveryDTO.getActivity_id());
        Student student = studentService.findStudentByIdentifier(deliveryDTO.getStudent_identifier());
        Optional<ActDelivery> actDelivery = actDeliveryRepository.findByActivityAndStudent(activity, student);
        if(actDelivery.isEmpty()){
            log.error("This student doesn't have this activity");
            throw new ResourceNotFoundException("Not found activity for this student.");
        }
        return actDelivery.get();
    }

    public ActDelivery addContentToDeliveryByStudent(ActDeliveryDTO deliveryDTO){
        Student student = studentService.findStudentByIdentifier(deliveryDTO.getStudent_identifier());
        Activity activity = activityService.findActivityById(deliveryDTO.getActivity_id());
        Optional<ActDelivery> actDelivery = actDeliveryRepository.findByActivityAndStudent(activity, student);
        if(actDelivery.isEmpty()){
            log.error("This student doesn't have this activity");
            throw new ResourceNotFoundException("Not found activity for this student.");
        }
        actDelivery.get().setContent(deliveryDTO.getContent());
        actDelivery.get().setDeliveryDate(deliveryDTO.getDelivery_date());
        actDeliveryRepository.save(actDelivery.get());
        return actDelivery.get();
    }

    //Extra methods.
    public ActDeliveryDTO transformActDelivery(ActDelivery delivery){
        ActDeliveryDTO response = new ActDeliveryDTO(
                delivery.getStudent().getIdentifier(),
                delivery.getActivity().getId(),
                delivery.getNote(),
                delivery.getContent(),
                delivery.getDeliveryDate(),
                studentService.getProfileStudent(delivery.getStudent())
        );
        return response;
    }
}
