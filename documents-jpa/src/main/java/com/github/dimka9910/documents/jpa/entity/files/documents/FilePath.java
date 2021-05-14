package com.github.dimka9910.documents.jpa.entity.files.documents;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@ToString(exclude = "parent")
public class FilePath {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Long size;
    private String path;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_concrete_document")
    private ConcreteDocument parent;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdTime;

}
