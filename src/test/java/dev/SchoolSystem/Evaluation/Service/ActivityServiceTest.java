package dev.SchoolSystem.Evaluation.Service;

import dev.SchoolSystem.Classroom.Entity.Classroom;
import dev.SchoolSystem.Classroom.Entity.Record;
import dev.SchoolSystem.Classroom.Repository.RecordRepository;
import dev.SchoolSystem.Classroom.Service.RecordService;
import dev.SchoolSystem.Evaluation.DTO.NewActivityDTO;
import dev.SchoolSystem.Evaluation.Entity.Activity;
import dev.SchoolSystem.Evaluation.Repository.ActivityRepository;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ActivityServiceTest {

    @Mock
    private ActivityRepository activityRepository;
    @Mock
    private RecordService recordService;
    @InjectMocks
    private ActivityService underTest;
    private Record record;
    private Classroom classroom;

    @BeforeEach
    void setUp() {
        underTest = new ActivityService(activityRepository, recordService);
    }

    @Test
    void testCreateActivity() {
        //given
        record = new Record(classroom, new HashSet<>());
        NewActivityDTO activityDTO = new NewActivityDTO("", "CL-300");
        //when
        when(recordService.findRecordByClassCode("CL-300")).thenReturn(record);
        underTest.createActivity(activityDTO);
        ArgumentCaptor<Activity> activityArgumentCaptor =
                ArgumentCaptor.forClass(Activity.class);
        verify(activityRepository).save(activityArgumentCaptor.capture());
        Activity capturedActivity = activityArgumentCaptor.getValue();
        //then
        assertInstanceOf(Activity.class, capturedActivity);
    }

    @Test
    void testFindNonExistActivity() {
        //given
        Long id = 1L;
        //when
        when(activityRepository.findById(id)).thenReturn(Optional.empty());
        //then
        Exception ex = assertThrowsExactly(ResourceNotFoundException.class,
                ()-> underTest.findActivityById(id));
        assertEquals("ActivityService: Activity not found", ex.getMessage());
    }
}