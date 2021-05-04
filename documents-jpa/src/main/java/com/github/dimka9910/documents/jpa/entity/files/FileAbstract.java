package com.github.dimka9910.documents.jpa.entity.files;

import com.github.dimka9910.documents.jpa.entity.files.catalogues.Catalogue;
import com.github.dimka9910.documents.jpa.entity.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
public abstract class FileAbstract{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "parent_id")
    protected Catalogue parent_id;

    @Temporal(TemporalType.DATE)
    protected Date created_time;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "created_by")
    protected User created_by;

    protected String name;

    @ElementCollection
    protected List<Long> readWritePermissionUsers;

    @ElementCollection
    protected List<Long> readPermissionUsers;

    @Override
    public String toString() {
        return "FileAbstract{" +
                "id=" + id +
                ", parent_id=" + parent_id +
                ", created_time=" + created_time +
                ", created_by=" + created_by +
                ", name='" + name + '\'' +
                '}';
    }
}
