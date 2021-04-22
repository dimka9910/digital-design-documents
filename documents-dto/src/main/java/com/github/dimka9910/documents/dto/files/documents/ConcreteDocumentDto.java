package com.github.dimka9910.documents.dto.files.documents;

import lombok.Builder;
import lombok.Data;

import java.io.File;
import java.util.Set;

@Data
@Builder
public class ConcreteDocumentDto {
    private Long id;
    private String name;
    private String description;
    private Set<FilePathDto> data;
}
