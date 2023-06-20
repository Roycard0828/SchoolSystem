package dev.SchoolSystem.Evaluation.Controller;

import dev.SchoolSystem.Evaluation.DTO.ActDeliveryDTO;
import dev.SchoolSystem.Evaluation.DTO.DeliverDeliveryDTO;
import dev.SchoolSystem.Evaluation.Entity.ActDelivery;
import dev.SchoolSystem.Evaluation.Service.ActDeliveryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/classroom/record/activity/act-delivery")
@CrossOrigin
@RequiredArgsConstructor
public class ActDeliveryController {

    @Autowired
    public ActDeliveryService actDeliveryService;

    @GetMapping("/{activityId}")
    @PreAuthorize("hasRole('ROLE_TEACHER')")
    public ResponseEntity<?> getAllDeliveries(@PathVariable("activityId") Long activityId){
        return new ResponseEntity<>(actDeliveryService.findDeliveriesByActivity(activityId),
                                    HttpStatus.OK);
    }

    @PostMapping("/add-note")
    @PreAuthorize("hasRole('ROLE_TEACHER')")
    public ResponseEntity<?> addNoteToDelivery(@Valid @RequestBody ActDeliveryDTO deliveryDTO){
        return new ResponseEntity<>(actDeliveryService.addNoteToDeliveryByTeacher(deliveryDTO),
                                    HttpStatus.OK);
    }

    @PostMapping("/get-delivery")
    @PreAuthorize("hasRole('ROLE_STUDENT')")
    public ResponseEntity<?> getStudentDelivery(@Valid @RequestBody DeliverDeliveryDTO deliveryDTO){
        return new ResponseEntity<>(actDeliveryService.getActDeliveryByStudent(
                deliveryDTO.getActivityId(), deliveryDTO.getStudentIdentifier()), HttpStatus.OK);
    }

    @PostMapping("/add-content")
    @PreAuthorize("hasRole('ROLE_STUDENT')")
    public ResponseEntity<?> addContentToDelivery(@Valid @RequestBody DeliverDeliveryDTO deliveryDTO){
        return new ResponseEntity<>(actDeliveryService.addContentToDeliveryByStudent(deliveryDTO),
                                    HttpStatus.OK);
    }

}
