package com.github.dimka9910.documents.jpa.daoImpl;

import com.github.dimka9910.documents.dao.DocumentTypeDao;
import com.github.dimka9910.documents.dto.files.documents.DocumentTypeDto;
import com.github.dimka9910.documents.jpa.entity.files.documents.DocumentType;
import com.github.dimka9910.documents.jpa.exceprions.IdNotFoundException;
import com.github.dimka9910.documents.jpa.repository.DocumentTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class DocumentTypeDaoJpa implements DocumentTypeDao {

    @Autowired
    DocumentTypeRepository documentTypeRepository;
    @PersistenceContext
    private EntityManager em;

    @Override
    public List<DocumentTypeDto> getAllDocumentTypes() {
        return documentTypeRepository.findAll().stream().map(v ->
                DocumentTypeDto.builder().name(v.getName()).id(v.getId()).build()).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public DocumentTypeDto addNewDocumentType(DocumentTypeDto documentTypeDto) {
        DocumentType documentType = documentTypeRepository.findByName(documentTypeDto.getName().toLowerCase()).orElse(null);
        if (documentType == null) {
            documentType = new DocumentType(documentTypeDto.getName().toLowerCase());
            em.persist(documentType);
        }
        return DocumentTypeDto.builder().id(documentType.getId()).name(documentType.getName()).build();
    }

    @Override
    public DocumentTypeDto getDocumentTypeByType(String name) {
        DocumentType documentType = documentTypeRepository.findByName(name.toLowerCase()).orElse(null);
        if (documentType == null) {
            return DocumentTypeDto.builder().build();
        }
        return DocumentTypeDto.builder().id(documentType.getId()).name(documentType.getName()).build();
    }

    @Override
    public DocumentTypeDto getDocumentTypeById(Long id) {
        DocumentType documentType = documentTypeRepository.findById(id).orElse(null);
        if (documentType == null) {
            return DocumentTypeDto.builder().build();
        }
        return DocumentTypeDto.builder().id(documentType.getId()).name(documentType.getName()).build();
    }

    @Override
    public Long deleteDocumentType(DocumentTypeDto documentTypeDto) {
        if (documentTypeDto.getId() != null)
            documentTypeRepository.deleteById(documentTypeDto.getId());
        else
            throw new IdNotFoundException();
        return 0L;
    }
}
