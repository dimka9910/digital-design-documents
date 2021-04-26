package com.github.dimka9910.documents.dto.files;

import com.github.dimka9910.documents.dto.user.UserDto;
import lombok.*;

import java.sql.Timestamp;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class FileAbstractDto {
    protected Long id;
    protected Long parent_id;
    protected Timestamp created_time;
    protected UserDto created_by;
    protected Set<UserDto> readWritePermissionedUsers;
    protected Set<UserDto> readPermissionedUsers;
    protected String name;
    protected TypeOfFileEnum typeOfFile;
}
