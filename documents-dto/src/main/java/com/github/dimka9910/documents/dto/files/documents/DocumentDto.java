package com.github.dimka9910.documents.dto.files.documents;

import com.github.dimka9910.documents.dto.files.FileAbstractDto;
import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;


@Data
public class DocumentDto extends FileAbstractDto {
    private String documentType;
    private String priority;

    private ConcreteDocumentDto topVersionDocument;

    @Builder
    public DocumentDto(Long id, Long parentId, Timestamp createdTime, Long userCreatedById, String name, String typeOfFile, String documentType, String priority, ConcreteDocumentDto topVersionDocument) {
        super(id, parentId, createdTime, userCreatedById, name, typeOfFile);
        this.documentType = documentType;
        this.priority = priority;
        this.topVersionDocument = topVersionDocument;
    }
}
