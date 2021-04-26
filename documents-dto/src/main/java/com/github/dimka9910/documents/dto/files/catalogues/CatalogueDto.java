package com.github.dimka9910.documents.dto.files.catalogues;

import com.github.dimka9910.documents.dto.files.FileAbstractDto;
import com.github.dimka9910.documents.dto.files.TypeOfFileEnum;
import com.github.dimka9910.documents.dto.user.UserDto;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.sql.Timestamp;
import java.util.Set;

@Data
public class CatalogueDto extends FileAbstractDto{

    private Set<FileAbstractDto> innerFiles;

    @Builder
    public CatalogueDto(Long id, Long parent_id, Timestamp created_time, UserDto created_by, Set<UserDto> readWritePermissionedUsers, Set<UserDto> readPermissionedUsers, String name, TypeOfFileEnum typeOfFile, Set<FileAbstractDto> innerFiles) {
        super(id, parent_id, created_time, created_by, readWritePermissionedUsers, readPermissionedUsers, name, typeOfFile);
        this.innerFiles = innerFiles;
    }


    @Override
    public String toString() {
        return "CatalogueDto{" +
                "innerFiles=" + innerFiles +
                ", id=" + id +
                ", parent_id=" + parent_id +
                ", created_time=" + created_time +
                ", readWritePermissionedUsers=" + readWritePermissionedUsers +
                ", readPermissionedUsers=" + readPermissionedUsers +
                ", name='" + name + '\'' +
                ", typeOfFile=" + typeOfFile +
                '}';
    }
}
