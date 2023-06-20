package dev.SchoolSystem.Evaluation.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.SchoolSystem.Classroom.Entity.Record;
import dev.SchoolSystem.Evaluation.DTO.NewActivityDTO;
import dev.SchoolSystem.Evaluation.Entity.Activity;
import dev.SchoolSystem.Evaluation.Service.ActivityService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import javax.servlet.http.HttpServletRequest;

import java.util.HashSet;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ActivityController.class)
class ActivityControllerTest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private HttpServletRequest request;
    @MockBean
    private ActivityService activityService;

    private Activity activity;
    private Record record;

    @BeforeEach
    void setUp() {
        activity = new Activity("activity", new HashSet<>(), new Record());
        record = new Record();
    }

    @Test
    @WithMockUser(username = "JOHN", authorities = { "ROLE_TEACHER" })
    void testCreateActivity() throws Exception {
        //given
        NewActivityDTO activityDTO = new NewActivityDTO("activity", "CL-300");
        String jsonActivityDTO = objectMapper.writeValueAsString(activityDTO);
        //when
        when(activityService.createActivity(activityDTO)).thenReturn(activity);
        //then
        mvc.perform(post("/classroom/record/activity")
                .contentType("application/json")
                .content(jsonActivityDTO))
                .andExpect(status().isCreated())
                .andExpect(content().string("Classroom created"));
    }
}