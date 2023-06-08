package dev.SchoolSystem.Classroom.Repository;

import dev.SchoolSystem.Classroom.Entity.Classroom;
import dev.SchoolSystem.Classroom.Entity.Record;
import dev.SchoolSystem.Student.Entity.Student;
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
class RecordRepositoryTest {

    @Autowired
    private RecordRepository underTest;
    @Autowired
    private SubjectRepository subjectRepository;
    @Autowired
    private ClassroomRepository classroomRepository;

    Subject subject;
    Classroom classroom;
    Set<Student> students;
    Record record;

    @BeforeEach
    void setUp() {
        subject = new Subject(1, "", 1);
        subjectRepository.save(subject);
        classroom = new Classroom("CL-200", subject, null);
        classroomRepository.save(classroom);
        students = new HashSet<>();
        record = new Record(classroom, students);
    }

    @Test
    void testCreateRecord(){
        assertNotNull(underTest.save(record));
    }

    @Test
    void testFindByClassroom(){
        underTest.save(record);
        assertNotNull(underTest.findRecordByClassroom(classroom));
    }

    @Test
    void testFindByClassroomNumber(){
        underTest.save(record);
        assertNotNull(underTest.findRecordByClassCode(classroom.getClassCode()));
    }

}