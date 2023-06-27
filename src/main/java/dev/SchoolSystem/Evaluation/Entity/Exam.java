package dev.SchoolSystem.Evaluation.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import dev.SchoolSystem.Classroom.Entity.Record;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "exam")
@Getter @Setter
@AllArgsConstructor
public class Exam {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "description")
    private String description;
    @Column(name = "content")
    private String content;
    @OneToMany(mappedBy = "exam", cascade = CascadeType.ALL)
    private Set<ExamAnswer> answers;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "record_id", nullable = false)
    private Record record;

    public Exam(String description, String content, Set<ExamAnswer> answers, Record record) {
        this.description = description;
        this.content = content;
        this.answers = answers;
        this.record = record;
    }

    public Exam(){}

    @JsonBackReference
    public Record getRecord(){
        return this.record;
    }

    @JsonManagedReference
    public Set<ExamAnswer> getAnswers(){
        return this.answers;
    }

}
