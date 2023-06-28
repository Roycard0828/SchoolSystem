package dev.SchoolSystem.Evaluation.Repository;

import dev.SchoolSystem.Evaluation.Entity.ActDelivery;
import dev.SchoolSystem.Evaluation.Entity.Activity;
import dev.SchoolSystem.Student.Entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.Set;

public interface ActDeliveryRepository extends JpaRepository<ActDelivery, Long> {

    @Query("SELECT a FROM ActDelivery a WHERE a.activity = ?1 AND a.student = ?2")
    Optional<ActDelivery> findByActivityAndStudent(Activity activity, Student student);

    @Query("SELECT a FROM ActDelivery a WHERE a.activity = ?1")
    Set<ActDelivery> findByActivity(Activity activity);

}
