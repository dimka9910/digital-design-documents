package com.github.dimka9910.documents.dto.files;

import com.github.dimka9910.documents.dto.AbstractDto;
import com.github.dimka9910.documents.dto.user.UserDto;
import lombok.*;

import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class FileAbstractDto implements AbstractDto {
    protected Long id;
    protected Long parent_id;
    protected Timestamp created_time;
    protected Long created_by;
    protected List<Long> readWritePermissionedUsers;
    protected List<Long> readPermissionedUsers;
    protected String name;
    protected TypeOfFileEnum typeOfFile;
}
