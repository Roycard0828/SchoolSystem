package dev.SchoolSystem.Classroom.Service;

import dev.SchoolSystem.Classroom.DTO.ClassRoomDTO;
import dev.SchoolSystem.Classroom.Entity.Classroom;
import dev.SchoolSystem.Classroom.Repository.ClassroomRepository;
import dev.SchoolSystem.Subejct.Entity.Subject;
import dev.SchoolSystem.Subejct.Service.SubjectService;
import dev.SchoolSystem.Teacher.Entity.Teacher;
import dev.SchoolSystem.Teacher.Service.TeacherService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
    @Mock
    private SubjectService subjectService;
    @Mock
    private TeacherService teacherService;
    @InjectMocks
    private ClassroomService underTest;

    private Subject subject;
    private Teacher teacher;

    @BeforeEach
    void setUp() {
        underTest = new ClassroomService(
                classroomRepository,
                recordService,
                subjectService,
                teacherService);
    }

    @Test
    void testCreateClassRoom(){
        //given
        ClassRoomDTO classRoomDTO = new ClassRoomDTO("cl-200", "", "");
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
        Classroom searchedClassroom = underTest.getClassroomByClassCode(classCode);
        //then
        assertInstanceOf(Classroom.class, searchedClassroom);
    }

}