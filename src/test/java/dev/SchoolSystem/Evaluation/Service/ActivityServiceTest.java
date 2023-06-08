package dev.SchoolSystem.Evaluation.Service;

import dev.SchoolSystem.Auth.Entity.User;
import dev.SchoolSystem.Classroom.Entity.Classroom;
import dev.SchoolSystem.Classroom.Entity.Record;
import dev.SchoolSystem.Classroom.Repository.RecordRepository;
import dev.SchoolSystem.Classroom.Service.RecordService;
import dev.SchoolSystem.Evaluation.DTO.NewActivityDTO;
import dev.SchoolSystem.Evaluation.Entity.Activity;
import dev.SchoolSystem.Evaluation.Repository.ActivityRepository;
import dev.SchoolSystem.Student.Entity.Student;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ActivityServiceTest {

    @Mock
    private ActivityRepository activityRepository;
    @Mock
    private RecordRepository recordRepository;
    @InjectMocks
    private ActivityService underTest;
    private Record record;
    private Classroom classroom;

    @BeforeEach
    void setUp() {
        underTest = new ActivityService(activityRepository, recordRepository);
    }

    @Test
    void testCreateActivity() {
        //given
        record = new Record(classroom, new HashSet<>());
        NewActivityDTO activityDTO = new NewActivityDTO("", record);
        //when
        underTest.createActivity(activityDTO);
        ArgumentCaptor<Activity> activityArgumentCaptor =
                ArgumentCaptor.forClass(Activity.class);
        verify(activityRepository).save(activityArgumentCaptor.capture());
        Activity capturedActivity = activityArgumentCaptor.getValue();
        //then
        assertInstanceOf(Activity.class, capturedActivity);
    }

    @Test
    void testFindAllActivitiesByRecordClass() throws Exception {
        //given
        record = new Record(classroom, new HashSet<>());
        String classCode = "CL-200";
        //when
        when(recordRepository.findRecordByClassCode(classCode)).thenReturn(Optional.of(record));
        //then
        assertInstanceOf(Set.class, underTest.findAllActivitiesByRecordClassCode(classCode));
    }

    @Test
    void testFindActivitiesOfNonExistRecord() {
        String classCode = "non-exist";
        //when
        when(recordRepository.findRecordByClassCode(classCode)).thenReturn(Optional.ofNullable(record));
        //then
        Exception exception = assertThrowsExactly(Exception.class,
                ()-> underTest.findAllActivitiesByRecordClassCode(classCode));
        assertEquals("Record not found in the database", exception.getMessage());
    }
}