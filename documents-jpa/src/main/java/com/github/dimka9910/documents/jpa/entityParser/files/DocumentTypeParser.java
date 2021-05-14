package com.github.dimka9910.documents.jpa.entityParser.files;

import com.github.dimka9910.documents.dto.files.documents.DocumentTypeDto;
import com.github.dimka9910.documents.jpa.entity.files.documents.DocumentType;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

@Component
public class DocumentTypeParser {
    public static DocumentTypeDto EtoDTO(DocumentType documentType) {
        return DocumentTypeDto.builder()
                .id(documentType.getId())
                .name(documentType.getName())
                .build();
    }

    public static DocumentType DTOtoE(DocumentTypeDto documentTypeDto){
        return new DocumentType(documentTypeDto.getName());
    }

    public List<DocumentTypeDto> fromList(List<DocumentType> list) {
        List<DocumentTypeDto> documentTypeDto = new LinkedList<>();
        list.forEach(v -> {
            documentTypeDto.add(EtoDTO(v));
        });
        return documentTypeDto;
    }

}
