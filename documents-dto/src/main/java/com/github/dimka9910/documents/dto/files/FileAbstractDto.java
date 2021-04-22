package com.github.dimka9910.documents.dto.files;

import com.github.dimka9910.documents.dto.user.UserDto;
import lombok.*;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class FileAbstractDto {
    protected Long id;
    protected Long parent_id;
    protected Set<UserDto> readWritePermissionedUsers;
    protected Set<UserDto> readPermissionedUsers;
    protected String name;
    protected TypeOfFileEnum typeOfFile;
}
