package dev.SchoolSystem.Evaluation.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.SchoolSystem.Evaluation.DTO.Exam.ExamDTO;
import dev.SchoolSystem.Evaluation.Entity.Exam;
import dev.SchoolSystem.Evaluation.Service.ExamAnswerService;
import dev.SchoolSystem.Evaluation.Service.ExamService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import javax.servlet.http.HttpServletRequest;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ExamController.class)
class ExamControllerTest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private HttpServletRequest request;
    @MockBean
    private ExamService examService;
    @MockBean
    private ExamAnswerService answerService;

    @Test
    @WithMockUser(username = "JOHN", authorities = { "ROLE_TEACHER" })
    void testCreateExam() throws Exception {
        //given
        ExamDTO examDTO = new ExamDTO("description", "content", "CL-300");
        String jsonAnswerDTO = objectMapper.writeValueAsString(examDTO);
        //then
        mvc.perform(post("/classroom/record/exam")
                .contentType("application/json")
                .content(jsonAnswerDTO))
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser(username = "JOHN", authorities = { "ROLE_USER" })
    void testGetExam() throws Exception {
        //given
        Exam exam = new Exam();
        Long examId = 1L;
        //when
        when(examService.findExamById(1L)).thenReturn(exam);
        //then
        mvc.perform(get("/classroom/record/exam/{examId}", examId))
                .andExpect(status().isOk());
    }
}