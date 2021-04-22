package com.github.dimka9910.documents.dto.files.documents;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DocumentTypeDto {
    private Long id;
    private String name;
}
