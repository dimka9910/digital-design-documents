package com.github.dimka9910.documents.jpa.daoImpl;

import com.github.dimka9910.documents.dao.DocumentTypeDao;
import com.github.dimka9910.documents.dto.files.documents.DocumentTypeDto;
import com.github.dimka9910.documents.jpa.entity.files.documents.DocumentType;
import com.github.dimka9910.documents.jpa.repository.DocumentTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DocumentTypeDaoJpa implements DocumentTypeDao {

    @Autowired
    DocumentTypeRepository documentTypeRepository;

    @Override
    public List<DocumentTypeDto> getAllDocumentTypes() {
        return null;
    }

    @Override
    public DocumentTypeDto addNewDocumentType(DocumentTypeDto documentTypeDto) {
        return null;
    }

    @Override
    public DocumentTypeDto getDocumentTypeByType(String name) {
        return null;
    }

    @Override
    public DocumentTypeDto getDocumentTypeById(Long id) {
        return null;
    }

    @Override
    public Long deleteDocumentType(DocumentTypeDto documentTypeDto) {
        return null;
    }
}
