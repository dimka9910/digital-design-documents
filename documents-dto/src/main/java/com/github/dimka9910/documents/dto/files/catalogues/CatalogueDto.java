package com.github.dimka9910.documents.dto.files.catalogues;

import com.github.dimka9910.documents.dto.files.FileAbstractDto;
import com.github.dimka9910.documents.dto.files.TypeOfFileEnum;
import com.github.dimka9910.documents.dto.user.UserDto;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.util.Set;

@Data
public class CatalogueDto extends FileAbstractDto{

    private Set<FileAbstractDto> innerFiles;

    @Builder
    public CatalogueDto(Long id, Long parent_id, Set<UserDto> readWritePermissionedUsers, Set<UserDto> readPermissionedUsers, String name, TypeOfFileEnum typeOfFile, Set<FileAbstractDto> innerFiles) {
        super(id, parent_id, readWritePermissionedUsers, readPermissionedUsers, name, typeOfFile);
        this.innerFiles = innerFiles;
    }

    @Override
    public String toString() {
        return "CatalogueDto{" +
                "innerFiles=" + innerFiles +
                ", id=" + id +
                ", parent_id=" + parent_id +
                ", readWritePermissionedUsers=" + readWritePermissionedUsers +
                ", readPermissionedUsers=" + readPermissionedUsers +
                ", name='" + name + '\'' +
                ", typeOfFile=" + typeOfFile +
                '}';
    }
}
