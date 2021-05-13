package com.github.dimka9910.documents.dto.files.documents;

import com.github.dimka9910.documents.dto.AbstractDto;
import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data
@Builder
public class ConcreteDocumentDto implements AbstractDto {
    private Long id;
    private String name;
    private String description;
    private Long version;
    private Timestamp modifiedTime;
    private Long userModifiedBy;
    private Long parentDocumentId;
    private List<FilePathDto> data;

}
