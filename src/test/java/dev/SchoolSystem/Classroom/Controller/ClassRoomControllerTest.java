package dev.SchoolSystem.Classroom.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.SchoolSystem.Classroom.DTO.ClassRoomDTO;
import dev.SchoolSystem.Classroom.DTO.NewRecordDTO;
import dev.SchoolSystem.Classroom.Entity.Classroom;
import dev.SchoolSystem.Classroom.Service.ClassroomService;
import dev.SchoolSystem.Classroom.Service.RecordService;
import dev.SchoolSystem.Subejct.Service.SubjectService;
import dev.SchoolSystem.Teacher.Service.TeacherService;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ClassRoomController.class)
class ClassRoomControllerTest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private HttpServletRequest request;
    @MockBean
    private ClassroomService classroomService;
    @MockBean
    private SubjectService subjectService;
    @MockBean
    private TeacherService teacherService;
    @MockBean
    private RecordService recordService;

    Classroom classroom;

    @BeforeEach
    void setUp() {
        classroom = new Classroom("CL-200", null, null);
    }

    @Test
    @WithMockUser(username = "JOHN", authorities = { "ROLE_MANAGER" })
    void testCreateClassroom() throws Exception {
        //given
        NewRecordDTO recordDTO = new NewRecordDTO(classroom);
        ClassRoomDTO classRoomDTO = new ClassRoomDTO("CL-200",
                "GEO-2001", "TECH-0001");
        String jsonStringDTO = objectMapper.writeValueAsString(classRoomDTO);
        //when
        when(subjectService.findByIdentifier("GEO-2001")).thenReturn(null);
        when(teacherService.findTeacherByIdentifier("TECH-0001")).thenReturn(null);
        when(recordService.createRecord(recordDTO)).thenReturn(null);
        //then
        mvc.perform(post("/classroom")
                .contentType("application/json")
                .content(jsonStringDTO))
                .andExpect(status().isCreated())
                .andExpect(content().string("Classroom created"));
    }

    @Test
    @WithMockUser(username = "JOHN", authorities = { "ROLE_MANAGER" })
    void testInvalidClassroomDTO() throws Exception {
        //given
        ClassRoomDTO classRoomDTO = new ClassRoomDTO("CL-200",
                "GEO-2001", ""); //Not blank validation for teacher
        String jsonStringDTO = objectMapper.writeValueAsString(classRoomDTO);
        //then
        mvc.perform(post("/classroom")
                .contentType("application/json")
                .content(jsonStringDTO))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(username = "JOHN", authorities = { "ROLE_MANAGER" })
    void testGetClassroomByClassCode() throws Exception {
        //given
        String classCode = "CL-200";
        String jsonClassroom = objectMapper.writeValueAsString(classroom);
        //when
        when(classroomService.getClassroomByClassCode(classCode)).thenReturn(classroom);
        //then
        mvc.perform(get("/classroom/CL-200"))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonClassroom));
    }
}