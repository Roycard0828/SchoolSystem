package dev.SchoolSystem.Subejct.Repository;

import dev.SchoolSystem.Subejct.Entity.Subject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class SubjectRepositoryTest {

    @Autowired
    private SubjectRepository underTest;

    @Test
    void testFindByName(){
        String name = "Mathematics";
        Subject subject = new Subject("", name, 10);
        underTest.save(subject);

        assertNotNull(underTest.findByName(name));
    }

    @Test
    void testFindByIdentifier(){
        String identifier = "identifier";
        Subject subject = new Subject(identifier, "mathematics", 10);
        underTest.save(subject);

        assertNotNull(underTest.findByIdentifier(identifier));
    }
}