package com.github.dimka9910.documents.dto.files.documents;

import com.github.dimka9910.documents.dto.AbstractDto;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.sql.Timestamp;
import java.util.Date;

@Data
@Builder
public class FilePathDto implements AbstractDto {
    private Long id;
    private String name;
    private Long size;
    @NotBlank
    private String path;
    private Long parentConcreteDocumentId;
    private Date createdTime;
}
