package dev.SchoolSystem.Evaluation.Service;

import dev.SchoolSystem.Classroom.Entity.Record;
import dev.SchoolSystem.Evaluation.DTO.ExamAnswerDTO;
import dev.SchoolSystem.Evaluation.Entity.ActDelivery;
import dev.SchoolSystem.Evaluation.Entity.Activity;
import dev.SchoolSystem.Evaluation.Entity.Exam;
import dev.SchoolSystem.Evaluation.Entity.ExamAnswer;
import dev.SchoolSystem.Evaluation.Repository.ExamAnswerRepository;
import dev.SchoolSystem.Evaluation.Repository.ExamRepository;
import dev.SchoolSystem.Student.Entity.Student;
import dev.SchoolSystem.Student.Service.StudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ExamAnswerServiceTest {

    @Mock
    private ExamAnswerRepository examAnswerRepository;
    @Mock
    private ExamService examService;
    @Mock
    private StudentService studentService;
    @InjectMocks
    private ExamAnswerService underTest;

    private Record record;
    private Student student;
    private Exam exam;
    private ExamAnswer examAnswer;

    @BeforeEach
    void setUp() {
        underTest = new ExamAnswerService(examAnswerRepository, examService, studentService);
    }

    @Test
    void testCreateExamAnswer() {
        //given
        ExamAnswerDTO answerDTO = new ExamAnswerDTO("S1900", 1L);
        exam = new Exam("", "", new HashSet<>(), record);
        //when
        when(studentService.findStudentByIdentifier("S1900")).thenReturn(student);
        when(examService.findExamById(answerDTO.getExamId())).thenReturn(Optional.ofNullable(exam));
        underTest.createAnswer(answerDTO);
        //then
        ArgumentCaptor<ExamAnswer> examAnswerArgumentCaptor =
                ArgumentCaptor.forClass(ExamAnswer.class);
        verify(examAnswerRepository).save(examAnswerArgumentCaptor.capture());
        ExamAnswer capturedAnswer = examAnswerArgumentCaptor.getValue();
        assertInstanceOf(ExamAnswer.class, capturedAnswer);
    }

    @Test
    void testFindAllAnswersByExam() throws Exception {
        //given
        Long id = 1L;
        exam = new Exam("", "", new HashSet<>(), record);
        //when
        when(examService.findExamById(id)).thenReturn(Optional.ofNullable(exam));
        Set<ExamAnswer> answers = underTest.findAllAnswersByExam(id);
        //then
        assertNotNull(answers);
        assertInstanceOf(Set.class, answers);
    }

    @Test
    void testAddNoteToExamByTeacher() throws Exception {
        //given
        double note = 10;
        ExamAnswerDTO answerDTO = new ExamAnswerDTO("S1900", 1L);
        exam = new Exam("", "", new HashSet<>(), record);
        examAnswer = new ExamAnswer(exam, student);
        //when
        when(studentService.findStudentByIdentifier(answerDTO.getStudentIdentifier())).thenReturn(student);
        when(examService.findExamById(answerDTO.getExamId())).thenReturn(Optional.ofNullable(exam));
        when(examAnswerRepository.findByExamAndStudent(exam, student)).thenReturn(examAnswer);
        underTest.addNoteToExamByTeacher(answerDTO, note);
        //then
        ArgumentCaptor<ExamAnswer> answerArgumentCaptor =
                ArgumentCaptor.forClass(ExamAnswer.class);
        verify(examAnswerRepository).save(answerArgumentCaptor.capture());
        ExamAnswer capturedAnswer = answerArgumentCaptor.getValue();
        assertNotNull(capturedAnswer);
    }

    @Test
    void testAddNoteToNonExistExam() {
        //given
        double note = 10;
        ExamAnswerDTO answerDTO = new ExamAnswerDTO("S-1900", 1L);
        //when
        when(studentService.findStudentByIdentifier(answerDTO.getStudentIdentifier())).thenReturn(student);
        when(examService.findExamById(answerDTO.getExamId())).thenReturn(Optional.ofNullable(exam));
        //then
        Exception ex = assertThrowsExactly(Exception.class,
                ()-> underTest.addNoteToExamByTeacher(answerDTO, note));
        assertEquals("Exam not found", ex.getMessage());
    }

    @Test
    void testGetExamAnswerByStudent() {
        //given
        Long id = 1L;
        String studentIdentifier = "S1900";
        exam = new Exam("", "", new HashSet<>(), record);
        examAnswer = new ExamAnswer(exam, student);
        //when
        when(examService.findExamById(id)).thenReturn(Optional.ofNullable(exam));
        when(studentService.findStudentByIdentifier(studentIdentifier)).thenReturn(student);
        when(examAnswerRepository.findByExamAndStudent(exam, student)).thenReturn(examAnswer);
        ExamAnswer capturedExamAnswer = underTest.getExamAnswerByStudent(id, studentIdentifier);
        //then
        assertNotNull(capturedExamAnswer);
    }

    @Test
    void testAddAnswerToExamByStudent() throws Exception {
        //given
        String newContent = "new content";
        Date answerDate = new Date();
        ExamAnswerDTO answerDTO = new ExamAnswerDTO("S1900", 1L);
        exam = new Exam("", "", new HashSet<>(), record);
        examAnswer = new ExamAnswer(exam, student);
        //when
        when(studentService.findStudentByIdentifier(answerDTO.getStudentIdentifier())).thenReturn(student);
        when(examService.findExamById(answerDTO.getExamId())).thenReturn(Optional.ofNullable(exam));
        when(examAnswerRepository.findByExamAndStudent(exam, student)).thenReturn(examAnswer);
        underTest.addContentToExamAnswerByStudent(answerDTO, newContent, answerDate);
        //then
        ArgumentCaptor<ExamAnswer> answerArgumentCaptor =
                ArgumentCaptor.forClass(ExamAnswer.class);
        verify(examAnswerRepository).save(answerArgumentCaptor.capture());
        ExamAnswer capturedAnswer = answerArgumentCaptor.getValue();
        assertNotNull(capturedAnswer);
    }

    @Test
    void testAddAnswerToNonExistExamAnswer() {
        //given
        String newContent = "new content";
        Date answerDate = new Date();
        ExamAnswerDTO answerDTO = new ExamAnswerDTO("S-1900", 1L);
        //when
        when(studentService.findStudentByIdentifier(answerDTO.getStudentIdentifier())).thenReturn(student);
        when(examService.findExamById(answerDTO.getExamId())).thenReturn(Optional.empty());
        //then
        Exception ex = assertThrowsExactly(Exception.class,
                ()-> underTest.addContentToExamAnswerByStudent(answerDTO, newContent, answerDate));
        assertEquals("ExamAnswer not found", ex.getMessage());
    }
}