package dev.SchoolSystem.Evaluation.Service;

import dev.SchoolSystem.Classroom.Entity.Classroom;
import dev.SchoolSystem.Classroom.Entity.Record;
import dev.SchoolSystem.Classroom.Repository.RecordRepository;
import dev.SchoolSystem.Evaluation.DTO.NewExamDTO;
import dev.SchoolSystem.Evaluation.Entity.Activity;
import dev.SchoolSystem.Evaluation.Entity.Exam;
import dev.SchoolSystem.Evaluation.Repository.ExamRepository;
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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ExamServiceTest {

    @Mock
    private ExamRepository examRepository;
    @Mock
    private RecordRepository recordRepository;
    @InjectMocks
    private ExamService underTest;

    private Record record;
    private Classroom classroom;

    @BeforeEach
    void setUp() {
        underTest = new ExamService(examRepository, recordRepository);
    }

    @Test
    void testCreateExam() {
        //given
        record = new Record(classroom, new HashSet<>());
        NewExamDTO examDTO = new NewExamDTO("", "", "CL-200");
        //when
        when(recordRepository.findRecordByClassCode(examDTO.getClassCode())).thenReturn(Optional.ofNullable(record));
        underTest.createExam(examDTO);
        ArgumentCaptor<Exam> examArgumentCaptor =
                ArgumentCaptor.forClass(Exam.class);
        verify(examRepository).save(examArgumentCaptor.capture());
        Exam capturedExam = examArgumentCaptor.getValue();
        //then
        assertInstanceOf(Exam.class, capturedExam);
    }

    @Test
    void testFindAllExamsByRecordClass() throws Exception {
        //given
        record = new Record(classroom, new HashSet<>());
        String classCode = "CL-200";
        //when
        when(recordRepository.findRecordByClassCode(classCode)).thenReturn(Optional.ofNullable(record));
        Set<Exam> exams = underTest.findAllExamsByRecordClassCode(classCode);
        //then
        assertInstanceOf(Set.class, exams);
    }

    @Test
    void testFindExamsOfNonExistRecord() {
        String classCode = "non-exist";
        //when
        when(recordRepository.findRecordByClassCode(classCode)).thenReturn(Optional.ofNullable(record));
        //then
        Exception exception = assertThrowsExactly(Exception.class,
                ()-> underTest.findAllExamsByRecordClassCode(classCode));
        assertEquals("Exam not found in the database", exception.getMessage());
    }
}