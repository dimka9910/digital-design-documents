package com.github.dimka9910.documents.dto.files.documents;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FilePathDto {
    private Long id;
    private String path;
    private Long parent_id;
}
