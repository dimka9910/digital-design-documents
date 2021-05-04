package com.github.dimka9910.documents.dto.files.catalogues;

import com.github.dimka9910.documents.dto.files.FileAbstractDto;
import com.github.dimka9910.documents.dto.files.TypeOfFileEnum;
import com.github.dimka9910.documents.dto.user.UserDto;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

@Data
public class CatalogueDto extends FileAbstractDto{


    @Builder
    public CatalogueDto(Long id, Long parent_id, Timestamp created_time, Long created_by, List<Long> readWritePermissionedUsers, List<Long> readPermissionedUsers, String name, TypeOfFileEnum typeOfFile) {
        super(id, parent_id, created_time, created_by, readWritePermissionedUsers, readPermissionedUsers, name, typeOfFile);
    }

    @Override
    public String toString() {
        return "CatalogueDto{" +
                "id=" + id +
                ", parent_id=" + parent_id +
                ", created_time=" + created_time +
                ", created_by=" + created_by +
                ", readWritePermissionedUsers=" + readWritePermissionedUsers +
                ", readPermissionedUsers=" + readPermissionedUsers +
                ", name='" + name + '\'' +
                ", typeOfFile=" + typeOfFile +
                '}';
    }
}
