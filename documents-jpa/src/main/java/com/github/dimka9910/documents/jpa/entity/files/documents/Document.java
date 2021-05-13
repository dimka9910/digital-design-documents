package com.github.dimka9910.documents.jpa.entity.files.documents;


import com.github.dimka9910.documents.jpa.entity.files.FileAbstract;
import com.github.dimka9910.documents.jpa.entity.files.catalogues.Catalogue;
import com.github.dimka9910.documents.jpa.entity.user.User;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@DiscriminatorValue("DOCUMENT")
public class Document extends FileAbstract {

    @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
    private DocumentType documentType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PriorityEnum priority;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private ConcreteDocument topVersionDocument;

    @OneToMany(mappedBy = "parent", fetch = FetchType.LAZY)
    private List<ConcreteDocument> concreteDocuments = new ArrayList<>();;
}
