package dev.SchoolSystem.Subejct.Repository;

import dev.SchoolSystem.Subejct.Entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubjectRepository extends JpaRepository<Subject, Long> {

    Subject findByName(String name);

    Subject findByIdentifier(int identifier);
}
