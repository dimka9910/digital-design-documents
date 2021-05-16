package com.github.dimka9910.documents.jpa.entity.files;

import com.github.dimka9910.documents.jpa.entity.files.catalogues.Catalogue;
import com.github.dimka9910.documents.jpa.entity.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@Entity
@NoArgsConstructor
@DiscriminatorColumn(name = "type")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class FileAbstract{

    @Id
    @TableGenerator(
            name = "ID_GEN",
            table = "ID_Generator",
            pkColumnName = "name",
            valueColumnName = "sequence",
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "ID_GEN")
    protected Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_parent")
    protected Catalogue parentCatalogue;

    @Temporal(TemporalType.TIMESTAMP)
    @JoinColumn(name = "created_time")
    protected Date createdTime;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_user")
    protected User userCreatedBy;

    @ManyToMany
    protected Set<User> readPermissionUsers = new HashSet<>();

    @ManyToMany
    protected Set<User> readWritePermissionUsers = new HashSet<>();
}
