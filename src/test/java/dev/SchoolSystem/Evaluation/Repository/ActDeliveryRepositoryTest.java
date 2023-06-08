package dev.SchoolSystem.Evaluation.Repository;

import dev.SchoolSystem.Auth.Entity.User;
import dev.SchoolSystem.Auth.Repository.UserRepository;
import dev.SchoolSystem.Classroom.Entity.Classroom;
import dev.SchoolSystem.Classroom.Entity.Record;
import dev.SchoolSystem.Classroom.Repository.ClassroomRepository;
import dev.SchoolSystem.Classroom.Repository.RecordRepository;
import dev.SchoolSystem.Evaluation.Entity.ActDelivery;
import dev.SchoolSystem.Evaluation.Entity.Activity;
import dev.SchoolSystem.Student.Entity.Student;
import dev.SchoolSystem.Student.Repository.StudentRepository;
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
class ActDeliveryRepositoryTest {

    @Autowired
    private ActDeliveryRepository underTest;
    @Autowired
    private ActivityRepository activityRepository;
    @Autowired
    private RecordRepository recordRepository;
    @Autowired
    private ClassroomRepository classroomRepository;
    @Autowired
    private SubjectRepository subjectRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private UserRepository userRepository;

    Subject subject;
    Classroom classroom;
    Record record;
    Set<ActDelivery> deliveries;
    Activity activity;
    ActDelivery actDelivery;
    Student student;
    User user;

    @BeforeEach
    void setUp() {
        //All the object necessary to save an ActDelivery
        subject = new Subject(1, "", 0);
        subjectRepository.save(subject);

        classroom = new Classroom("CL-200", subject, null);
        classroomRepository.save(classroom);

        record = new Record(classroom, null);
        recordRepository.save(record);

        deliveries = new HashSet<>();
        activity = new Activity("", deliveries, record);
        activityRepository.save(activity);

        user = new User("", "", "", "");
        userRepository.save(user);

        student = new Student(10, "", "", user);
        studentRepository.save(student);

        actDelivery = new ActDelivery(student, activity);
    }

    @Test
    void testSaveActDelivery(){
        assertInstanceOf(ActDelivery.class, underTest.save(actDelivery));
    }

    @Test
    void testFindActDeliveryByRecordAndStudent(){
        underTest.save(actDelivery);
        assertNotNull(underTest.findByActivityAndStudent(activity, student));
        assertEquals(actDelivery, underTest.findByActivityAndStudent(activity, student));
    }

}