package com.github.dimka9910.documents.dto.files;

import com.github.dimka9910.documents.dto.files.catalogues.CatalogueDto;
import com.github.dimka9910.documents.dto.user.UserDto;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
public abstract class FileAbstractDto {
    protected Long id;
    protected CatalogueDto parent;
    protected Set<UserDto> readWritePermissionedUsers;
    protected Set<UserDto> readPermissionedUsers;
    protected String name;
    protected TypeOfFileEnum typeOfFile;
}
