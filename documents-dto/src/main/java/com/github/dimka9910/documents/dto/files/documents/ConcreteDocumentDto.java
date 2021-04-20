package com.github.dimka9910.documents.dto.files.documents;

import java.io.File;
import java.util.Set;


public class ConcreteDocumentDto {
    private Long id;
    private String name;
    private String description;
    private Set<FilePathDto> data;
}
