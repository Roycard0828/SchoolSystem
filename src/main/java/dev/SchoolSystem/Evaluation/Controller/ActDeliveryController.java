package dev.SchoolSystem.Evaluation.Controller;

import dev.SchoolSystem.Evaluation.DTO.Activity.ActDeliveryDTO;
import dev.SchoolSystem.Evaluation.Entity.ActDelivery;
import dev.SchoolSystem.Evaluation.Service.ActDeliveryService;
import dev.SchoolSystem.Student.Service.StudentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/classroom/record/activity/act-delivery")
@CrossOrigin
@RequiredArgsConstructor
@Slf4j
public class ActDeliveryController {

    @Autowired
    public ActDeliveryService actDeliveryService;

    @PostMapping("/add-note")
    @PreAuthorize("hasRole('ROLE_TEACHER')")
    public ResponseEntity<?> addNoteToDelivery(@Valid @RequestBody ActDeliveryDTO deliveryDTO){
        return new ResponseEntity<>(actDeliveryService.addNoteToDeliveryByTeacher(deliveryDTO),
                                    HttpStatus.OK);
    }

    @PostMapping("/get-delivery")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<?> getStudentDelivery(@Valid @RequestBody ActDeliveryDTO deliveryDTO){
        ActDelivery actDelivery = actDeliveryService.getActDeliveryByStudent(deliveryDTO);
        ActDeliveryDTO response = actDeliveryService.transformActDelivery(actDelivery);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/add-content")
    @PreAuthorize("hasRole('ROLE_STUDENT')")
    public ResponseEntity<?> addContentToDelivery(@Valid @RequestBody ActDeliveryDTO deliveryDTO){
        return new ResponseEntity<>(actDeliveryService.addContentToDeliveryByStudent(deliveryDTO),
                                    HttpStatus.OK);
    }

}
