package dev.SchoolSystem.Evaluation.Entity;

import dev.SchoolSystem.Classroom.Entity.Record;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "activity")
@Getter @Setter
@AllArgsConstructor
public class Activity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "description")
    private String description;
    @OneToMany(mappedBy = "activity", cascade = CascadeType.ALL)
    private Set<ActivityDelivery> deliveries;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "record_id", nullable = false)
    private Record record;

    public Activity(String description, Set<ActivityDelivery> deliveries) {
        this.description = description;
        this.deliveries = deliveries;
    }
}
