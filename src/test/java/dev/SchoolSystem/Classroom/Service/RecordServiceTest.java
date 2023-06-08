package dev.SchoolSystem.Classroom.Service;

import dev.SchoolSystem.Auth.Entity.User;
import dev.SchoolSystem.Classroom.DTO.NewRecordDTO;
import dev.SchoolSystem.Classroom.Entity.Classroom;
import dev.SchoolSystem.Classroom.Entity.Record;
import dev.SchoolSystem.Classroom.Repository.RecordRepository;
import dev.SchoolSystem.Evaluation.Entity.Activity;
import dev.SchoolSystem.Evaluation.Entity.Exam;
import dev.SchoolSystem.Student.Entity.Student;
import dev.SchoolSystem.Student.Repository.StudentRepository;
import dev.SchoolSystem.Student.Service.StudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RecordServiceTest {

    @Mock
    private RecordRepository recordRepository;
    @Mock
    private StudentRepository studentRepository;
    @InjectMocks
    private RecordService underTest;
    private Classroom classroom;
    private Student student;

    @BeforeEach
    void setUp() {
        underTest = new RecordService(recordRepository, studentRepository);
    }

    @Test
    void testCreateRecord(){
        //given
        NewRecordDTO recordDTO = new NewRecordDTO(classroom);
        //when
        underTest.createRecord(recordDTO);
        //then
        ArgumentCaptor<Record> recordArgumentCaptor =
                ArgumentCaptor.forClass(Record.class);
        verify(recordRepository).save(recordArgumentCaptor.capture());
        Record capturedRecord = recordArgumentCaptor.getValue();

        assertInstanceOf(Record.class, capturedRecord);
    }

    @Test
    void testFindRecordByClassCode() throws Exception {
        //given
        String classCode = "cl-200";
        given(recordRepository.findRecordByClassCode(classCode)).
                willReturn(Optional.of(new Record(classroom, new HashSet<>())));
        //when
        Optional<Record> record = underTest.findRecordByClassCode(classCode);
        //then
        assertTrue(record.isPresent());
        assertInstanceOf(Record.class, record.get());
    }

    @Test
    void testFailsWhenSendNotExitsClassCode() throws Exception {
        //given
        String classCode = "non-exits";
        //when
        when(recordRepository.findRecordByClassCode(classCode)).
                thenReturn(Optional.empty());
        //then
        assertThrowsExactly(Exception.class,
                ()-> underTest.findRecordByClassCode(classCode), "Record not found");
    }

    @Test
    void testGetAllActivitiesRecordByClassCode(){
        //given
        HashSet<Activity> activities = new HashSet<>(); //simulate activities list
        String classCode = "cl-200";
        Record record = new Record(classroom, new HashSet<>());
        record.setActivities(activities);
        given(recordRepository.findRecordByClassCode(classCode)).
                willReturn(Optional.of(record));
        //when
        Set<Activity> capturedActivities = underTest.getAllActivitiesByRecordClassCode(classCode);
        //then
        assertNotNull(capturedActivities);
    }

    @Test
    void testGetAllExamsRecordByClassCode(){
        //given
        HashSet<Exam> exam = new HashSet<>(); //simulate activities list
        String classCode = "cl-200";
        Record record = new Record(classroom, new HashSet<>());
        record.setExams(exam);
        given(recordRepository.findRecordByClassCode(classCode)).
                willReturn(Optional.of(record));
        //when
        Set<Exam> capturedExams = underTest.getAllExamsByRecordClassCode(classCode);
        //then
        assertNotNull(capturedExams);
    }

    @Test
    void testAddOneStudentToRecord() throws Exception {
        //given
        String studentIdentifier = "S-123";
        String classCode = "cl-200";
        Record record = new Record(classroom, new HashSet<>());
        student = new Student(10, "", "",
                new User());
        //when
        when(studentRepository.findByIdentifier(studentIdentifier)).thenReturn(student);
        when(recordRepository.findRecordByClassCode(classCode)).thenReturn(Optional.of(record));
        underTest.addStudentToRecord(studentIdentifier, classCode);
        //then
        assertEquals(1, record.getStudents().size());
    }

    @Test
    void testAddIncorrectStudentToRecord(){
        //given
        String studentIdentifier = "non-exists";
        String classCode = "cl-200";
        Record record = new Record(classroom, new HashSet<>());
        //when
        when(studentRepository.findByIdentifier(studentIdentifier)).thenReturn(student);
        when(recordRepository.findRecordByClassCode(classCode)).thenReturn(Optional.of(record));
        //then
        Exception ex = assertThrowsExactly(Exception.class,
                ()->underTest.addStudentToRecord(studentIdentifier, classCode));
        assertEquals("Student does not exist", ex.getMessage());
    }

    @Test
    void testAddStudentToNonExistRecord(){
        //given
        String studentIdentifier = "S-123";
        String classCode = "cl-200";
        student = new Student(10, "", "",
                new User());
        //when
        when(studentRepository.findByIdentifier(studentIdentifier)).thenReturn(student);
        when(recordRepository.findRecordByClassCode(classCode)).thenReturn(Optional.empty());
        //then
        Exception ex = assertThrowsExactly(Exception.class,
                ()->underTest.addStudentToRecord(studentIdentifier, classCode));
        assertEquals("Record not found", ex.getMessage());
    }

}