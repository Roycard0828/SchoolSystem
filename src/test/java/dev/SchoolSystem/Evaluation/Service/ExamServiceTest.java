package dev.SchoolSystem.Evaluation.Service;

import dev.SchoolSystem.Classroom.Entity.Classroom;
import dev.SchoolSystem.Classroom.Entity.Record;
import dev.SchoolSystem.Classroom.Repository.RecordRepository;
import dev.SchoolSystem.Classroom.Service.RecordService;
import dev.SchoolSystem.Evaluation.DTO.NewExamDTO;
import dev.SchoolSystem.Evaluation.Entity.Exam;
import dev.SchoolSystem.Evaluation.Repository.ExamRepository;
import dev.SchoolSystem.Util.Exceptions.ResourceNotFoundException;
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
    private RecordService recordService;
    @Mock
    private RecordRepository recordRepository;
    @InjectMocks
    private ExamService underTest;

    private Record record;
    private Classroom classroom;

    @BeforeEach
    void setUp() {
        underTest = new ExamService(examRepository, recordService);
    }

    @Test
    void testCreateExam() {
        //given
        record = new Record(classroom, new HashSet<>());
        NewExamDTO examDTO = new NewExamDTO("", "", "CL-200");
        //when
        when(recordService.findRecordByClassCode(examDTO.getClass_code())).thenReturn(record);
        underTest.createExam(examDTO);
        ArgumentCaptor<Exam> examArgumentCaptor =
                ArgumentCaptor.forClass(Exam.class);
        verify(examRepository).save(examArgumentCaptor.capture());
        Exam capturedExam = examArgumentCaptor.getValue();
        //then
        assertInstanceOf(Exam.class, capturedExam);
    }

    @Test
    void testFindAllExamsByRecordClass(){
        //given
        record = new Record(classroom, new HashSet<>());
        String classCode = "CL-200";
        //when
        when(recordService.findRecordByClassCode(classCode)).thenReturn(record);
        Set<Exam> exams = underTest.findAllExamsByRecordClassCode(classCode);
        //then
        assertInstanceOf(Set.class, exams);
    }
}