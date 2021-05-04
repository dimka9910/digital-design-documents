package com.github.dimka9910.documents.jpa.entity.files.documents;


import com.github.dimka9910.documents.jpa.entity.files.FileAbstract;
import com.github.dimka9910.documents.jpa.entity.files.catalogues.Catalogue;
import com.github.dimka9910.documents.jpa.entity.user.User;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@Entity
public class Document extends FileAbstract {

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private DocumentType documentType;

    @Enumerated(EnumType.ORDINAL)
    @Column(nullable = false)
    private PriorityEnum priority;

    @OneToMany(mappedBy = "parent_id", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ConcreteDocument> versionsOfDocuments;

    public Document(Long id, Catalogue parent_id, Date created_time, User created_by, String name, List<Long> readWritePermissionUsers, List<Long> readPermissionUsers, DocumentType documentType, PriorityEnum priority) {
        super(id, parent_id, created_time, created_by, name, readWritePermissionUsers, readPermissionUsers);
        this.documentType = documentType;
        this.priority = priority;
    }
}
