package dev.SchoolSystem.Evaluation.Controller;

import dev.SchoolSystem.Classroom.Service.RecordService;
import dev.SchoolSystem.Evaluation.DTO.NewActivityDTO;
import dev.SchoolSystem.Evaluation.Service.ActivityService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/classroom/record/activity")
@CrossOrigin
@RequiredArgsConstructor
public class ActivityController {

    @Autowired
    private ActivityService activityService;

    @PostMapping()
    @PreAuthorize("hasRole('ROLE_TEACHER')")
    public ResponseEntity<?> createActivity(@RequestBody NewActivityDTO activityDTO){
        activityService.createActivity(activityDTO);
        return new ResponseEntity<>("Activity created", HttpStatus.CREATED);
    }

    @GetMapping("/{activityId}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<?> getActivity(@PathVariable("activityId")Long activityId){
        return new ResponseEntity<>(activityService.findActivityById(activityId), HttpStatus.OK);
    }

}
