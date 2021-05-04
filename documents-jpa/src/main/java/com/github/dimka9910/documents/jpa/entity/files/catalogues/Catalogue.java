package com.github.dimka9910.documents.jpa.entity.files.catalogues;

import com.github.dimka9910.documents.jpa.entity.files.FileAbstract;
import com.github.dimka9910.documents.jpa.entity.user.User;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@NoArgsConstructor
@Table(name = "catalogue", uniqueConstraints =
        {
                @UniqueConstraint(columnNames = {"name", "parent_id"})
        })
public class Catalogue extends FileAbstract {

    public Catalogue(Long id, Catalogue parent_id, Date created_time, User created_by, String name, List<Long> readWritePermissionUsers, List<Long> readPermissionUsers) {
        super(id, parent_id, created_time, created_by, name, readWritePermissionUsers, readPermissionUsers);
    }
}
