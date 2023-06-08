package dev.SchoolSystem.Classroom.Repository;

import dev.SchoolSystem.Classroom.Entity.Classroom;
import dev.SchoolSystem.Classroom.Entity.Record;
import dev.SchoolSystem.Student.Entity.Student;
import dev.SchoolSystem.Student.Repository.StudentRepository;
import dev.SchoolSystem.Subejct.Entity.Subject;
import dev.SchoolSystem.Subejct.Repository.SubjectRepository;
import dev.SchoolSystem.Teacher.Entity.Teacher;
import dev.SchoolSystem.Teacher.Repository.TeacherRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ClassroomRepositoryTest {

    @Autowired
    private ClassroomRepository underTest;
    @Autowired
    private SubjectRepository subjectRepository;

    private Subject subject;
    private Classroom classroom;

    @BeforeEach
    void setUp() {
        subject = new Subject(1, "Subject", 0);
        subjectRepository.save(subject);
        classroom = new Classroom("CL-200", subject, null);
    }

    @Test
    void testSaveClassroomWithNotNullSubject(){
        assertInstanceOf(Classroom.class, underTest.save(classroom));
    }

    @Test
    void testFindClassroomByNumber(){
        underTest.save(classroom);
        assertNotNull(underTest.findByClassCode("CL-200"));
    }

}