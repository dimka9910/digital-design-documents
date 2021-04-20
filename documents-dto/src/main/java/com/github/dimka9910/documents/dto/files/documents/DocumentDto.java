package com.github.dimka9910.documents.dto.files.documents;


import java.util.Map;

public class DocumentDto {
    private DocumentTypeDto documentType;
    private PriorityEnum priority;
    private ConcreteDocumentDto topVersionDocument;
    private Map<Long, ConcreteDocumentDto> versionsOfDocuments;
}
