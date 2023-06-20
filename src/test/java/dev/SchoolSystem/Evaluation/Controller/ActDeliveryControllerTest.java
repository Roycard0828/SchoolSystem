package dev.SchoolSystem.Evaluation.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.SchoolSystem.Evaluation.DTO.ActDeliveryDTO;
import dev.SchoolSystem.Evaluation.DTO.DeliverDeliveryDTO;
import dev.SchoolSystem.Evaluation.Entity.ActDelivery;
import dev.SchoolSystem.Evaluation.Entity.Activity;
import dev.SchoolSystem.Evaluation.Service.ActDeliveryService;
import dev.SchoolSystem.Student.Entity.Student;
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

import java.util.Date;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ActDeliveryController.class)
class ActDeliveryControllerTest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private HttpServletRequest request;
    @MockBean
    private ActDeliveryService actDeliveryService;

    private Activity activity = new Activity();
    private ActDelivery actDelivery;

    @BeforeEach
    void setUp() {
        actDelivery = new ActDelivery(new Student(), activity);
    }

    @Test
    @WithMockUser(username = "JOHN", authorities = { "ROLE_TEACHER" })
    void testGetAllDeliveriesByActivity() throws Exception {
        //given
        Long id = 1l;
        //when
        when(actDeliveryService.findDeliveriesByActivity(id)).thenReturn(new HashSet<>());
        //then
        mvc.perform(get("/classroom/record/activity/act-delivery/1"))
                .andExpect(content().contentType("application/json"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "JOHN", authorities = { "ROLE_TEACHER" })
    void testAddNoteToActivity() throws Exception {
        //given
        ActDeliveryDTO actDeliveryDTO = new ActDeliveryDTO("studentIdentifier", 1L, 10.0);
        String jsonActDelivery = objectMapper.writeValueAsString(actDeliveryDTO);
        //when
        when(actDeliveryService.addNoteToDeliveryByTeacher(actDeliveryDTO)).thenReturn(actDelivery);
        //then
        mvc.perform(post("/classroom/record/activity/act-delivery/add-note")
                .contentType("application/json")
                .content(jsonActDelivery))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "JOHN", authorities = { "ROLE_STUDENT" })
    void testGetStudentDelivery() throws Exception {
        //given
        DeliverDeliveryDTO deliveryDTO = new DeliverDeliveryDTO("studentIdentifier", 1l);
        String jsonDelivery = objectMapper.writeValueAsString(deliveryDTO);
        String jsonActDelivery = objectMapper.writeValueAsString(actDelivery);
        //when
        when(actDeliveryService.getActDeliveryByStudent(deliveryDTO.getActivityId(),
                                                        deliveryDTO.getStudentIdentifier()))
                .thenReturn(actDelivery);
        //then
        mvc.perform(post("/classroom/record/activity/act-delivery/get-delivery")
                .contentType("application/json")
                .content(jsonDelivery))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonActDelivery));
    }

    @Test
    @WithMockUser(username = "JOHN", authorities = { "ROLE_STUDENT" })
    void testAddContentToDelivery() throws Exception {
        //given
        String content = "content";
        Date deliveryDate = new Date();
        DeliverDeliveryDTO deliveryDTO = new DeliverDeliveryDTO("studentIdentifier",
                1l,
                content,
                deliveryDate);

        String jsonDelivery = objectMapper.writeValueAsString(deliveryDTO);
        String jsonActDelivery = objectMapper.writeValueAsString(actDelivery);
        //when
        when(actDeliveryService.addContentToDeliveryByStudent(deliveryDTO))
                .thenReturn(actDelivery);
        //then
        mvc.perform(post("/classroom/record/activity/act-delivery/add-content")
                        .contentType("application/json")
                        .content(jsonDelivery))
                .andExpect(status().isOk());
    }

}