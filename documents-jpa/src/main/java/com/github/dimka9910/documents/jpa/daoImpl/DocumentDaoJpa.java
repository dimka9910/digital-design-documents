package com.github.dimka9910.documents.jpa.daoImpl;

import com.github.dimka9910.documents.dao.DocumentDao;
import com.github.dimka9910.documents.dto.files.catalogues.CatalogueDto;
import com.github.dimka9910.documents.dto.files.documents.ConcreteDocumentDto;
import com.github.dimka9910.documents.dto.files.documents.DocumentDto;
import com.github.dimka9910.documents.jpa.entityParser.files.CatalogueParser;
import com.github.dimka9910.documents.jpa.entityParser.files.DocumentParser;
import com.github.dimka9910.documents.jpa.exceprions.IdNotFoundException;
import com.github.dimka9910.documents.jpa.repository.CatalogueRepository;
import com.github.dimka9910.documents.jpa.repository.DocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Component
public class DocumentDaoJpa implements DocumentDao {
    @Autowired
    private CatalogueRepository catalogueRepository;
    @Autowired
    private DocumentRepository documentRepository;
    @Autowired
    private CatalogueParser catalogueParser;
    @Autowired
    private DocumentParser documentParser;
    @PersistenceContext
    private EntityManager em;

    @Override
    public List<DocumentDto> getAllDocuments() {
        return documentParser.fromList(documentRepository.findAll());
    }

    @Override
    public DocumentDto getDocumentById(Long id) {
        return documentParser.EtoDTO(documentRepository.findById(id).orElseThrow(IdNotFoundException::new));
    }

    @Override
    public DocumentDto addNewDocument(DocumentDto documentDto, CatalogueDto catalogueDto) {
        documentDto.setParent_id(catalogueDto.getId());
        return documentParser.EtoDTO(documentRepository.save(documentParser.DTOtoE(documentDto)));

    }

    @Override
    public DocumentDto modifyDocument(DocumentDto documentDto, ConcreteDocumentDto concreteDocumentDto) {
        documentDto.setName(concreteDocumentDto.getName());
        em.persist(documentParser.DTOtoE(documentDto));
        return documentParser.EtoDTO(
                documentRepository.findById(documentDto.getId())
                        .orElseThrow(IdNotFoundException::new)
        );
    }

    @Override
    public Long deleteDocument(DocumentDto documentDto) {
        documentRepository.deleteById(documentDto.getId());
        return 0L;
    }
}
