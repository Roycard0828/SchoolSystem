package dev.SchoolSystem.Evaluation.Repository;

import dev.SchoolSystem.Classroom.Entity.Classroom;
import dev.SchoolSystem.Classroom.Entity.Record;
import dev.SchoolSystem.Classroom.Repository.ClassroomRepository;
import dev.SchoolSystem.Classroom.Repository.RecordRepository;
import dev.SchoolSystem.Evaluation.Entity.ActDelivery;
import dev.SchoolSystem.Evaluation.Entity.Activity;
import dev.SchoolSystem.Subejct.Entity.Subject;
import dev.SchoolSystem.Subejct.Repository.SubjectRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ActivityRepositoryTest {

    @Autowired
    private ActivityRepository underTest;
    @Autowired
    private ActDeliveryRepository actDeliveryRepository;
    @Autowired
    private RecordRepository recordRepository;
    @Autowired
    private ClassroomRepository classroomRepository;
    @Autowired
    private SubjectRepository subjectRepository;

    Subject subject;
    Classroom classroom;
    Record record;
    Set<ActDelivery> deliveries;
    Activity activity;

    @BeforeEach
    void setUp() {
        subject = new Subject(1, "", 0);
        subjectRepository.save(subject);

        classroom = new Classroom("CL-200", subject, null);
        classroomRepository.save(classroom);

        record = new Record(classroom, null);
        recordRepository.save(record);
        deliveries = new HashSet<>();
        activity = new Activity("", deliveries, record);
    }

    @Test
    void testGetActivitiesByRecord(){
        assertNotNull(underTest.save(activity));
        assertInstanceOf(HashSet.class, underTest.findAllByRecord(record));
    }

}