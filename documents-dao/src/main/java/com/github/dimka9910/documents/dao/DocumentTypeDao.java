package com.github.dimka9910.documents.dao;

import com.github.dimka9910.documents.dto.files.documents.DocumentTypeDto;
import org.w3c.dom.DocumentType;

import java.util.List;

public interface DocumentTypeDao extends AbstractDao{
    List<DocumentTypeDto> getAllDocumentTypes();
    DocumentTypeDto addNewDocumentType(DocumentTypeDto documentTypeDto);
    DocumentTypeDto getDocumentTypeByType(String name);

    void deleteDocumentType(DocumentTypeDto documentTypeDto);
}
