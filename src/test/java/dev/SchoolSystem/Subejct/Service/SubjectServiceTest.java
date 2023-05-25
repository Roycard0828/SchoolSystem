package dev.SchoolSystem.Subejct.Service;

import dev.SchoolSystem.Subejct.DTO.SubjectDTO;
import dev.SchoolSystem.Subejct.Entity.Subject;
import dev.SchoolSystem.Subejct.Repository.SubjectRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SubjectServiceTest {

    @Mock
    private SubjectRepository subjectRepository;
    @InjectMocks
    private SubjectService underTest;

    @BeforeEach
    void setUp() {
        underTest = new SubjectService(subjectRepository);
    }

    @Test
    void testSaveSubject(){
        //given
        SubjectDTO subjectDTO = new SubjectDTO("Mathematics", 1234, 10);
        //when
        underTest.saveSubject(subjectDTO);
        //then
        ArgumentCaptor<Subject> subjectArgumentCaptor =
                ArgumentCaptor.forClass(Subject.class);
        verify(subjectRepository).save(subjectArgumentCaptor.capture());
        Subject capturedSubject = subjectArgumentCaptor.getValue();

        assertInstanceOf(Subject.class, capturedSubject);
    }

    @Test
    void testFindByName(){
        //given
        String name = "mathematics";
        Subject subject = new Subject(1234, name, 10);
        given(subjectRepository.findByName(name)).willReturn(subject);
        //when
        Subject serachSubject = underTest.findByName(name);
        //then
        assertNotNull(serachSubject);
    }

    @Test
    void testFindByIdentifier(){
        //given
        int identifier = 1234;
        Subject subject = new Subject(identifier, "mathematics", 10);
        given(subjectRepository.findByIdentifier(identifier)).willReturn(subject);
        //when
        Subject serachSubject = underTest.findByIdentifier(identifier);
        //then
        assertNotNull(serachSubject);
    }

}