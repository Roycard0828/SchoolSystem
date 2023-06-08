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
    private Set<ActDelivery> deliveries;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "record_id", nullable = false)
    private Record record;

    public Activity(String description, Set<ActDelivery> deliveries, Record record) {
        this.description = description;
        this.deliveries = deliveries;
        this.record = record;
    }
}
