package com.github.dimka9910.documents.dto.files.documents;

import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data
@Builder
public class DocumentVisualizationDto {
    private Long id;
    private String name;
    private String description;
    private String documentType;
    private String priority;
    private Long version;
    private Timestamp modifiedTime;
    private Timestamp createdTime;
    private Long parentCatalogueId;
    private List<FilePathDto> data;
}
