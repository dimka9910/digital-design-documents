package com.github.dimka9910.documents.jpa.entity.files.documents;

import com.github.dimka9910.documents.jpa.entity.user.User;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
public class ConcreteDocument{

    @Autowired
    @Transient
    EntityManager em;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String description;
    @Column(nullable = false)
    private Long version;

    @Temporal(TemporalType.DATE)
    protected Date modified_time;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "modified_by")
    protected User modified_by;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "parent_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    protected Document parent_id;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> data;


    public ConcreteDocument(Long id, String name, String description, Long version, Date modified_time, User modified_by, Document parent_id, List<String> data) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.version = version;
        this.modified_time = modified_time;
        this.modified_by = modified_by;
        this.parent_id = parent_id;
        this.data = data;
    }
}
