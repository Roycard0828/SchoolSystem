package dev.SchoolSystem.Subejct.Service;

import dev.SchoolSystem.Subejct.DTO.SubjectDTO;
import dev.SchoolSystem.Subejct.Entity.Subject;
import dev.SchoolSystem.Subejct.Repository.SubjectRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class SubjectService implements UserDetailsService {

    private final SubjectRepository subjectRepository;

    public Subject saveSubject(SubjectDTO subjectDto){
        Subject subject = subjectRepository.save(new Subject(
                subjectDto.getIdentifier(),
                subjectDto.getName(),
                subjectDto.getCredits()
        ));

        return subject;
    }

    public Subject findByName(String name){
        return subjectRepository.findByName(name);
    }

    public Subject findByIdentifier(int identifier){
        return subjectRepository.findByIdentifier(identifier);
    }

    public List<Subject> getAllSubjects(){
        return subjectRepository.findAll();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }
}
