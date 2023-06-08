package dev.SchoolSystem.Classroom.Service;

import dev.SchoolSystem.Auth.Entity.User;
import dev.SchoolSystem.Classroom.DTO.NewClassRoomDTO;
import dev.SchoolSystem.Classroom.Entity.Classroom;
import dev.SchoolSystem.Classroom.Repository.ClassroomRepository;
import dev.SchoolSystem.Subejct.Entity.Subject;
import dev.SchoolSystem.Teacher.Entity.Teacher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClassroomServiceTest {

    @Mock
    private ClassroomRepository classroomRepository;
    @Mock
    private RecordService recordService;
    @InjectMocks
    private ClassroomService underTest;

    private Subject subject;
    private Teacher teacher;

    @BeforeEach
    void setUp() {
        underTest = new ClassroomService(classroomRepository, recordService);
    }

    @Test
    void testCreateClassRoom(){
        //given
        NewClassRoomDTO classRoomDTO = new NewClassRoomDTO("cl-200", subject, teacher);
        //when
        underTest.createClass(classRoomDTO);
        ArgumentCaptor<Classroom> classroomArgumentCaptor =
                ArgumentCaptor.forClass(Classroom.class);
        verify(classroomRepository).save(classroomArgumentCaptor.capture());
        //then
        Classroom capturedClassroom = classroomArgumentCaptor.getValue();
        assertInstanceOf(Classroom.class, capturedClassroom);
    }

    @Test
    void testGetClassRoomByClassCode(){
        //given
        String classCode = "cl-200";
        given(classroomRepository.findByClassCode(classCode)).
                willReturn(Optional.of(new Classroom("", subject, teacher)));
        //when
        Optional<Classroom> searchedClassroom = underTest.getClassroomByNumber(classCode);
        //then
        assertTrue(searchedClassroom.isPresent());
    }

}