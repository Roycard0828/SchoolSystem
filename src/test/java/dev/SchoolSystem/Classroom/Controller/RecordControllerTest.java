package dev.SchoolSystem.Classroom.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.SchoolSystem.Classroom.DTO.AddStudentDTO;
import dev.SchoolSystem.Classroom.Entity.Record;
import dev.SchoolSystem.Classroom.Service.RecordService;
import dev.SchoolSystem.Evaluation.Entity.Activity;
import dev.SchoolSystem.Student.Service.StudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import javax.servlet.http.HttpServletRequest;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(RecordController.class)
class RecordControllerTest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private HttpServletRequest request;
    @MockBean
    private RecordService recordService;
    @MockBean
    private StudentService studentService;

    Record record;

    @BeforeEach
    void setUp() {
        record = new Record();
    }
    @Test
    void testGetRecordByClassCode() throws Exception {
        //given
        String classCode = "CL-200";
        //when
        when(recordService.findRecordByClassCode(classCode)).thenReturn(record);
        //then
        mvc.perform(get("/classroom/record/CL-200"))
                .andExpect(status().isOk());
    }

    @Test
    void testGetActivitiesByRecord() throws Exception {
        //given
        String classCode = "CL-200";
        //when
        when(recordService.findRecordByClassCode(classCode)).thenReturn(record);
        //then
        mvc.perform(get("/classroom/record/CL-200/activities"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"));
    }

    @Test
    void testGetExamsByRecord() throws Exception {
        //given
        String classCode = "CL-200";
        //when
        when(recordService.findRecordByClassCode(classCode)).thenReturn(record);
        //then
        mvc.perform(get("/classroom/record/CL-200/exams"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"));
    }

    @Test
    void testAddStudentToRecord() throws Exception {
        //given
        AddStudentDTO studentDTO = new AddStudentDTO("CL-300", "STU-0001");
        String jsonStudentDTO = objectMapper.writeValueAsString(studentDTO);
        //when
        //then
        mvc.perform(patch("/classroom/record/add-student")
                .contentType("application/json")
                .content(jsonStudentDTO))
                .andExpect(status().isOk())
                .andExpect(content().string("Student added"));
    }

    @Test
    void testInvalidDTOAddStudent() throws Exception {
        //given
        AddStudentDTO studentDTO = new AddStudentDTO("", "STU-0001"); //Not blank validation
        String jsonStudentDTO = objectMapper.writeValueAsString(studentDTO);
        //then
        mvc.perform(patch("/classroom/record/add-student")
                .contentType("application/json")
                .content(jsonStudentDTO))
                .andExpect(status().isBadRequest());
    }
}