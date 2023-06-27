package dev.SchoolSystem.Evaluation.Service;

import dev.SchoolSystem.Classroom.Entity.Record;
import dev.SchoolSystem.Evaluation.DTO.Exam.ExamAnswerDTO;
import dev.SchoolSystem.Evaluation.Entity.Exam;
import dev.SchoolSystem.Evaluation.Entity.ExamAnswer;
import dev.SchoolSystem.Evaluation.Repository.ExamAnswerRepository;
import dev.SchoolSystem.Student.Entity.Student;
import dev.SchoolSystem.Student.Service.StudentService;
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
        dev.SchoolSystem.Evaluation.DTO.Exam.ExamAnswerDTO answerDTO = new dev.SchoolSystem.Evaluation.DTO.Exam.ExamAnswerDTO("S1900", 1L);
        exam = new Exam("", "", new HashSet<>(), record);
        //when
        when(studentService.findStudentByIdentifier("S1900")).thenReturn(student);
        when(examService.findExamById(answerDTO.getExam_id())).thenReturn(exam);
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
        when(examService.findExamById(id)).thenReturn(exam);
        Set<ExamAnswer> answers = underTest.findAllAnswersByExam(id);
        //then
        assertNotNull(answers);
        assertInstanceOf(Set.class, answers);
    }

    @Test
    void testAddNoteToExamByTeacher(){
        //given
        double note = 10;
        dev.SchoolSystem.Evaluation.DTO.Exam.ExamAnswerDTO answerDTO = new dev.SchoolSystem.Evaluation.DTO.Exam.ExamAnswerDTO("S1900", 1L, 10);
        exam = new Exam("", "", new HashSet<>(), record);
        examAnswer = new ExamAnswer(exam, student);
        //when
        when(studentService.findStudentByIdentifier(answerDTO.getStudent_identifier())).thenReturn(student);
        when(examService.findExamById(answerDTO.getExam_id())).thenReturn(exam);
        when(examAnswerRepository.findByExamAndStudent(exam, student)).thenReturn(Optional.ofNullable(examAnswer));
        underTest.addNoteToExamByTeacher(answerDTO);
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
        dev.SchoolSystem.Evaluation.DTO.Exam.ExamAnswerDTO answerDTO = new dev.SchoolSystem.Evaluation.DTO.Exam.ExamAnswerDTO("S-1900", 1L, 10);
        //when
        when(studentService.findStudentByIdentifier(answerDTO.getStudent_identifier())).thenReturn(student);
        when(examService.findExamById(answerDTO.getExam_id())).thenReturn(null);
        //then
        Exception ex = assertThrowsExactly(ResourceNotFoundException.class,
                ()-> underTest.addNoteToExamByTeacher(answerDTO));
        assertEquals("ExamAnswerService: Exam Answer not found", ex.getMessage());
    }

    @Test
    void testGetExamAnswerByStudent() {
        //given
        Long id = 1L;
        String studentIdentifier = "S1900";
        exam = new Exam("", "", new HashSet<>(), record);
        examAnswer = new ExamAnswer(exam, student);
        //when
        when(examService.findExamById(id)).thenReturn(exam);
        when(studentService.findStudentByIdentifier(studentIdentifier)).thenReturn(student);
        when(examAnswerRepository.findByExamAndStudent(exam, student)).thenReturn(Optional.ofNullable(examAnswer));
        ExamAnswer capturedExamAnswer = underTest.getExamAnswerByStudent(id, studentIdentifier);
        //then
        assertNotNull(capturedExamAnswer);
    }

    @Test
    void testAddAnswerToExamByStudent() throws Exception {
        //given
        ExamAnswerDTO deliverAnswerDTO = new ExamAnswerDTO("S1900", 1L, "content");
        exam = new Exam("", "", new HashSet<>(), record);
        examAnswer = new ExamAnswer(exam, student);
        //when
        when(studentService.findStudentByIdentifier(deliverAnswerDTO.getStudent_identifier())).thenReturn(student);
        when(examService.findExamById(deliverAnswerDTO.getExam_id())).thenReturn(exam);
        when(examAnswerRepository.findByExamAndStudent(exam, student)).thenReturn(Optional.ofNullable(examAnswer));
        underTest.addContentToExamAnswerByStudent(deliverAnswerDTO);
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
        ExamAnswerDTO deliverAnswerDTO = new ExamAnswerDTO("S1900", 1L, "content");
        //when
        when(studentService.findStudentByIdentifier(deliverAnswerDTO.getStudent_identifier())).thenReturn(student);
        when(examService.findExamById(deliverAnswerDTO.getExam_id())).thenReturn(null);
        //then
        Exception ex = assertThrowsExactly(ResourceNotFoundException.class,
                ()-> underTest.addContentToExamAnswerByStudent(deliverAnswerDTO));
        assertEquals("ExamAnswerService: Exam Answer not found", ex.getMessage());
    }
}