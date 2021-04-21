package com.github.dimka9910.documents.dto.files.documents;
import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class DocumentDto {
    private DocumentTypeDto documentType;
    private PriorityEnum priority;
    private ConcreteDocumentDto topVersionDocument;
    private Map<Long, ConcreteDocumentDto> versionsOfDocuments;
}
