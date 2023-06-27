package dev.SchoolSystem.Evaluation.Repository;

import dev.SchoolSystem.Auth.Entity.User;
import dev.SchoolSystem.Auth.Repository.UserRepository;
import dev.SchoolSystem.Classroom.Entity.Classroom;
import dev.SchoolSystem.Classroom.Entity.Record;
import dev.SchoolSystem.Classroom.Repository.ClassroomRepository;
import dev.SchoolSystem.Classroom.Repository.RecordRepository;
import dev.SchoolSystem.Evaluation.Entity.Exam;
import dev.SchoolSystem.Evaluation.Entity.ExamAnswer;
import dev.SchoolSystem.Student.Entity.Student;
import dev.SchoolSystem.Student.Repository.StudentRepository;
import dev.SchoolSystem.Subejct.Entity.Subject;
import dev.SchoolSystem.Subejct.Repository.SubjectRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ExamAnswerRepositoryTest {

    @Autowired
    private ExamAnswerRepository underTest;
    @Autowired
    private ExamRepository examRepository;
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
    Exam exam;
    ExamAnswer examAnswer;
    Student student;
    User user;

    @BeforeEach
    void setUp() {
        subject = new Subject("identifier", "", 0);
        subjectRepository.save(subject);

        classroom = new Classroom("CL-200", subject, null);
        classroomRepository.save(classroom);

        record = new Record(classroom, null);
        recordRepository.save(record);

        exam = new Exam("", "", new HashSet<>(), record);
        examRepository.save(exam);

        user = new User("", "", "", "");
        userRepository.save(user);

        student = new Student("STU-1234",10, "", "", user);
        studentRepository.save(student);

        examAnswer = new ExamAnswer(exam, student);
    }

    @Test
    void testSaveExamAnswer() {
        assertNotNull(underTest.save(examAnswer));
    }

    @Test
    void testFindByRecordAndUser() {
        underTest.save(examAnswer);
        assertInstanceOf(ExamAnswer.class, underTest.findByExamAndStudent(exam, student));
    }
}