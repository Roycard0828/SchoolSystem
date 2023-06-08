package dev.SchoolSystem.Evaluation.Service;

import dev.SchoolSystem.Classroom.Entity.Record;
import dev.SchoolSystem.Evaluation.DTO.ActDeliveryDTO;
import dev.SchoolSystem.Evaluation.Entity.ActDelivery;
import dev.SchoolSystem.Evaluation.Entity.Activity;
import dev.SchoolSystem.Evaluation.Repository.ActDeliveryRepository;
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
class ActDeliveryServiceTest {

    @Mock
    private ActDeliveryRepository actDeliveryRepository;
    @Mock
    private ActivityService activityService;
    @Mock
    private StudentService studentService;
    @InjectMocks
    private ActDeliveryService underTest;

    private Student student;
    private Activity activity;
    private Record record;
    private ActDelivery actDelivery;

    @BeforeEach
    void setUp() {
        underTest = new ActDeliveryService(actDeliveryRepository, activityService,
                studentService);
    }

    @Test
    void testCreateActDelivery() {
        //given
        activity = new Activity("", new HashSet<>(), record);
        ActDeliveryDTO actDeliveryDTO = new ActDeliveryDTO("S1900", 1L);
        //when
        when(studentService.findStudentByIdentifier("S1900")).thenReturn(student);
        when(activityService.findActivityById(1L)).thenReturn(Optional.ofNullable(activity));
        underTest.createActDelivery(actDeliveryDTO);
        ArgumentCaptor<ActDelivery> actDeliveryArgumentCaptor =
                ArgumentCaptor.forClass(ActDelivery.class);
        verify(actDeliveryRepository).save(actDeliveryArgumentCaptor.capture());
        ActDelivery capturedActDelivery = actDeliveryArgumentCaptor.getValue();
        //then
        assertInstanceOf(ActDelivery.class, capturedActDelivery);

    }

    @Test
    void testFindDeliveriesByActivityId() throws Exception {
        //given
        Long id = 1L;
        activity = new Activity("", new HashSet<>(), record);
        //when
        when(activityService.findActivityById(id)).thenReturn(Optional.ofNullable(activity));
        Set<ActDelivery> deliveries = underTest.findDeliveriesByActivity(id);
        //then
        assertNotNull(deliveries);
        assertInstanceOf(Set.class, deliveries);
    }

    @Test
    void testAddNoteToDeliveryByTeacher() throws Exception {
        //given
        double note = 10;
        ActDeliveryDTO deliveryDTO = new ActDeliveryDTO("identifier", 1L);
        activity = new Activity("", new HashSet<>(), record);
        actDelivery = new ActDelivery(student, activity);
        //when
        when(studentService.findStudentByIdentifier(deliveryDTO.getStudentIdentifier())).thenReturn(student);
        when(activityService.findActivityById(deliveryDTO.getActivityId())).thenReturn(Optional.ofNullable(activity));
        when(actDeliveryRepository.findByActivityAndStudent(activity, student)).thenReturn(actDelivery);
        underTest.addNoteToDeliveryByTeacher(deliveryDTO, note);
        //then
        ArgumentCaptor<ActDelivery> deliveryArgumentCaptor =
                ArgumentCaptor.forClass(ActDelivery.class);
        verify(actDeliveryRepository).save(deliveryArgumentCaptor.capture());
        ActDelivery capturedDelivery = deliveryArgumentCaptor.getValue();
        assertNotNull(capturedDelivery);
    }

    @Test
    void testAddNoteToNonExistActivity() {
        //given
        double note = 10;
        ActDeliveryDTO deliveryDTO = new ActDeliveryDTO("identifier", 1L);
        //when
        when(studentService.findStudentByIdentifier(deliveryDTO.getStudentIdentifier())).thenReturn(student);
        when(activityService.findActivityById(deliveryDTO.getActivityId())).thenReturn(Optional.empty());
        //then
        Exception ex = assertThrowsExactly(Exception.class,
                ()-> underTest.addNoteToDeliveryByTeacher(deliveryDTO, note));
        assertEquals("Activity not found", ex.getMessage());
    }

    @Test
    void testGetDeliveryByStudent() {
        //given
        Long id = 1L;
        String studentIdentifier = "S1900";
        activity = new Activity("", new HashSet<>(), record);
        actDelivery = new ActDelivery(student, activity);
        //when
        when(activityService.findActivityById(id)).thenReturn(Optional.ofNullable(activity));
        when(studentService.findStudentByIdentifier(studentIdentifier)).thenReturn(student);
        when(actDeliveryRepository.findByActivityAndStudent(activity, student)).thenReturn(actDelivery);
        ActDelivery capturedActDelivery = underTest.getActDeliveryByStudent(id, studentIdentifier);
        //then
        assertNotNull(capturedActDelivery);
    }

    @Test
    void testAddContentToDeliveryByStudent() throws Exception {
        //given
        ActDeliveryDTO deliveryDTO = new ActDeliveryDTO("S1900", 1L);
        String newContent = "new content";
        Date deliveryDate = new Date();
        activity = new Activity("", new HashSet<>(), record);
        actDelivery = new ActDelivery(student, activity);
        //when
        when(studentService.findStudentByIdentifier(deliveryDTO.getStudentIdentifier())).thenReturn(student);
        when(activityService.findActivityById(deliveryDTO.getActivityId())).thenReturn(Optional.ofNullable(activity));
        when(actDeliveryRepository.findByActivityAndStudent(activity, student)).thenReturn(actDelivery);
        underTest.addContentToDeliveryByStudent(deliveryDTO, newContent, deliveryDate);
        //then
        ArgumentCaptor<ActDelivery> deliveryArgumentCaptor =
                ArgumentCaptor.forClass(ActDelivery.class);
        verify(actDeliveryRepository).save(deliveryArgumentCaptor.capture());
        ActDelivery capturedDelivery = deliveryArgumentCaptor.getValue();
        assertNotNull(capturedDelivery);
    }

    @Test
    void testAddContentToNonExistDelivery() {
        //given
        String newContent = "new content";
        Date deliveryDate = new Date();
        ActDeliveryDTO deliveryDTO = new ActDeliveryDTO("identifier", 1L);
        //when
        when(studentService.findStudentByIdentifier(deliveryDTO.getStudentIdentifier())).thenReturn(student);
        when(activityService.findActivityById(deliveryDTO.getActivityId())).thenReturn(Optional.empty());
        //then
        Exception ex = assertThrowsExactly(Exception.class,
                ()-> underTest.addContentToDeliveryByStudent(deliveryDTO, newContent, deliveryDate));
        assertEquals("ActivityDelivery not found", ex.getMessage());
    }
}