package com.github.dimka9910.documents.jpa.entity.files.catalogues;

import com.github.dimka9910.documents.jpa.entity.files.FileAbstract;
import com.github.dimka9910.documents.jpa.entity.user.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@NoArgsConstructor
@Data
@DiscriminatorValue("CATALOGUE")
@Table(name = "catalogue", uniqueConstraints =
        {
                @UniqueConstraint(columnNames = {"name", "fk_parent"})
        })
public class Catalogue extends FileAbstract {
    private String name;
}
