package dev.SchoolSystem.Subejct.Controller;

import dev.SchoolSystem.Auth.Entity.User;
import dev.SchoolSystem.Subejct.DTO.SubjectDTO;
import dev.SchoolSystem.Subejct.Entity.Subject;
import dev.SchoolSystem.Subejct.Service.SubjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestController
@RequestMapping("/subject")
@CrossOrigin
@RequiredArgsConstructor
public class SubjectController {

    @Autowired
    private SubjectService subjectService;


    @GetMapping("/get-all")
    public ResponseEntity<List<Subject>> getAllSubjects(){
        return new ResponseEntity<>(subjectService.getAllSubjects(), HttpStatus.OK);
    }

    @GetMapping("/get/{name}")
    public ResponseEntity<Subject> getSubjectByName(@PathVariable("name") String name){
        Subject subject = subjectService.findByName(name);
        if(subject != null){
            return new ResponseEntity<>(subject, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(null, BAD_REQUEST);
        }
    }

    @GetMapping("/get-by-identifier/{identifier}")
    public ResponseEntity<Subject> getSubjectByIdentifier(@PathVariable("identifier") int identifier){
        Subject subject = subjectService.findByIdentifier(identifier);
        if(subject != null){
            return new ResponseEntity<>(subject, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(null, BAD_REQUEST);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<Subject> createSubject(@Valid @RequestBody SubjectDTO subjectDTO){
        Subject subject = new Subject(subjectDTO.getIdentifier(), subjectDTO.getName(), subjectDTO.getCredits());
        return new ResponseEntity<>(subject, HttpStatus.CREATED);
    }

}
