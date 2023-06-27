package dev.SchoolSystem.Evaluation.Controller;

import dev.SchoolSystem.Evaluation.DTO.Activity.ActDeliveryDTO;
import dev.SchoolSystem.Evaluation.DTO.Activity.ActivityDTO;
import dev.SchoolSystem.Evaluation.Entity.ActDelivery;
import dev.SchoolSystem.Evaluation.Entity.Activity;
import dev.SchoolSystem.Evaluation.Service.ActDeliveryService;
import dev.SchoolSystem.Evaluation.Service.ActivityService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/classroom/record/activity")
@CrossOrigin
@RequiredArgsConstructor
public class ActivityController {

    @Autowired
    private ActivityService activityService;
    @Autowired
    private ActDeliveryService deliveryService;

    @PostMapping()
    @PreAuthorize("hasRole('ROLE_TEACHER')")
    public ResponseEntity<?> createActivity(@RequestBody ActivityDTO activityDTO){
        activityService.createActivity(activityDTO);
        return new ResponseEntity<>("Activity created", HttpStatus.CREATED);
    }

    @GetMapping("/{activityId}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<?> getActivity(@PathVariable("activityId")Long activityId){
        Activity activity = activityService.findActivityById(activityId);
        ActivityDTO response;
        Set<ActDeliveryDTO> deliveryResponses =  new HashSet<>();

        for (ActDelivery actDelivery: activity.getDeliveries()) {
            deliveryResponses.add(deliveryService.transformActDelivery(actDelivery));
        }
        response = new ActivityDTO(activity.getDescription(), deliveryResponses);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
