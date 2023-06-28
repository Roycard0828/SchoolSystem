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
        return new ResponseEntity<>(subjectService.findByName(name), HttpStatus.OK);
    }

    @GetMapping("/get-by-identifier/{identifier}")
    public ResponseEntity<Subject> getSubjectByIdentifier(@PathVariable("identifier") String identifier){
        return new ResponseEntity<>(subjectService.findByIdentifier(identifier), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<Subject> createSubject(@Valid @RequestBody SubjectDTO subjectDTO){
        Subject subject = subjectService.saveSubject(subjectDTO);
        return new ResponseEntity<>(subject, HttpStatus.CREATED);
    }

}
