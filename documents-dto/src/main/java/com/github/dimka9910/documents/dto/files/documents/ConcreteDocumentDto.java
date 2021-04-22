package com.github.dimka9910.documents.dto.files.documents;

import lombok.Builder;
import lombok.Data;

import java.io.File;
import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

@Data
@Builder
public class ConcreteDocumentDto {
    private Long id;
    private String name;
    private String description;
    private Long version;
    private Timestamp modified_time;
    private Long parent_id;
    private List<FilePathDto> data;
}
