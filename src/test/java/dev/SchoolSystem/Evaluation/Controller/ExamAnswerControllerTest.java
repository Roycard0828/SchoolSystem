package dev.SchoolSystem.Evaluation.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.SchoolSystem.Evaluation.DTO.Exam.ExamAnswerDTO;
import dev.SchoolSystem.Evaluation.Entity.ExamAnswer;
import dev.SchoolSystem.Evaluation.Service.ExamAnswerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import javax.servlet.http.HttpServletRequest;

import java.util.HashSet;
import java.util.Set;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ExamAnswerController.class)
class ExamAnswerControllerTest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private HttpServletRequest request;
    @MockBean
    private ExamAnswerService answerService;

    ExamAnswer answer;
    Set<ExamAnswer> answers;

    @BeforeEach
    void setUp() {
        answer = new ExamAnswer();
        answers = new HashSet<>();
    }

    @Test
    void testGetAllAnswersByExam() throws Exception {
        //given
        Long id = 1L;
        String jsonAnswers = objectMapper.writeValueAsString(answers);
        //when
        when(answerService.findAllAnswersByExam(id)).thenReturn(answers);
        //then
        mvc.perform(get("/classroom/record/exam/exam-answer/1"))
                .andExpect(content().contentType("application/json"))
                .andExpect(status().isOk());
    }

    @Test
    void testAddNoteToExam() throws Exception {
        //given
        ExamAnswerDTO answerDTO = new ExamAnswerDTO("STU-1900", 1L, 10.0);
        String jsonAnswerDTO = objectMapper.writeValueAsString(answerDTO);
        //when
        when(answerService.addNoteToExamByTeacher(answerDTO)).thenReturn(answer);
        //then
        mvc.perform(post("/classroom/record/exam/exam-answer/add-note")
                .contentType("application/json")
                .content(jsonAnswerDTO))
                .andExpect(status().isOk());
    }

    @Test
    void testGetStudentExamAnswer() throws Exception {
        //given
        ExamAnswerDTO answerDTO = new ExamAnswerDTO("STU-1900", 1L);
        String jsonAnswerDTO = objectMapper.writeValueAsString(answerDTO);
        String jsonAnswer = objectMapper.writeValueAsString(answer);
        //when
        when(answerService.getExamAnswerByStudent(answerDTO.getExam_id(), answerDTO.getStudent_identifier()))
                .thenReturn(answer);
        //then
        mvc.perform(post("/classroom/record/exam/exam-answer/get-answer")
                .contentType("application/json")
                .content(jsonAnswerDTO))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonAnswer));
    }

    @Test
    void testAddAnswerToExam() throws Exception {
        //given
        ExamAnswerDTO answerDTO = new ExamAnswerDTO("STU-1900", 1L, "answer");
        String jsonAnswerDTO = objectMapper.writeValueAsString(answerDTO);
        //then
        mvc.perform(post("/classroom/record/exam/exam-answer/add-answer")
                .contentType("application/json")
                .content(jsonAnswerDTO))
                .andExpect(status().isOk());
    }
}
