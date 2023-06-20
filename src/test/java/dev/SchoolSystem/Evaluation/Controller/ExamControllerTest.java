package dev.SchoolSystem.Evaluation.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.SchoolSystem.Evaluation.DTO.ExamAnswerDTO;
import dev.SchoolSystem.Evaluation.DTO.NewExamDTO;
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

import static org.junit.jupiter.api.Assertions.*;
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

    @Test
    @WithMockUser(username = "JOHN", authorities = { "ROLE_TEACHER" })
    void testCreateExam() throws Exception {
        //given
        NewExamDTO examDTO = new NewExamDTO("description", "content", "CL-300");
        String jsonAnswerDTO = objectMapper.writeValueAsString(examDTO);
        //then
        mvc.perform(post("/classroom/record/exam")
                .contentType("application/json")
                .content(jsonAnswerDTO))
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser(username = "JOHN", authorities = { "ROLE_TEACHER" })
    void testGetAllExamsByRecord() throws Exception {
        //given
        String classCode = "CL-300";
        //then
        mvc.perform(get("/classroom/record/exam/CL-300")
                .contentType("application/json"))
                .andExpect(status().isOk());
    }
}