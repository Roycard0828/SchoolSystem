package dev.SchoolSystem.Subejct.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.SchoolSystem.Subejct.DTO.SubjectDTO;
import dev.SchoolSystem.Subejct.Entity.Subject;
import dev.SchoolSystem.Subejct.Service.SubjectService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.User;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import javax.servlet.http.HttpServletRequest;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(SubjectController.class)
class SubjectControllerTest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private HttpServletRequest request;
    @MockBean
    private SubjectService subjectService;

    private User authU;
    private Subject subject;

    @BeforeEach
    void setUp() {
        subject = new Subject("1234", "mathematics", 10);
        authU = new User("username", "password", new ArrayList<>());
    }

    @Test
    void testGetAllSubject() throws Exception {
        mvc.perform(get("/subject/get-all"))
                .andExpect(content().contentType("application/json"))
                .andExpect(status().isOk());
    }

    @Test
    void testGetSubjectByName() throws Exception {
        String jsonString = objectMapper.writeValueAsString(subject);
        when(subjectService.findByName("mathematics")).thenReturn(subject);
        mvc.perform(get("/subject/get/mathematics"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().json(jsonString));
    }

    @Test
    void testFailsToIncorrectName() throws Exception {
        mvc.perform(get("/subject/get/non-name"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testGetSubjectByIdentifier() throws Exception {
        String jsonString = objectMapper.writeValueAsString(subject);
        when(subjectService.findByIdentifier("1234")).thenReturn(subject);
        mvc.perform(get("/subject/get-by-identifier/1234"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().json(jsonString));
    }

    @Test
    void testCreateSubject() throws Exception {
        String jsonString = objectMapper.writeValueAsString(subject);
        SubjectDTO subjectDTO = new SubjectDTO("mathematics", "identifier", 10);
        String jsonStringDTO = objectMapper.writeValueAsString(subjectDTO);
        System.out.println(jsonStringDTO);
        mvc.perform(post("/subject/create")
                .contentType("application/json")
                .content(jsonStringDTO))
                .andExpect(status().isCreated())
                .andExpect(content().json(jsonString));
    }

    @Test
    void testInvalidDTOCreateSubject() throws Exception{
        //Not blank validation is not fulfilled in the name
        SubjectDTO subjectDTO = new SubjectDTO("", "identifier", 10);
        String jsonStringDTO = objectMapper.writeValueAsString(subjectDTO);
        mvc.perform(post("/subject/create")
                        .contentType("application/json")
                        .content(jsonStringDTO))
                .andExpect(status().isBadRequest());
    }
}