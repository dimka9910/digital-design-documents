package com.github.dimka9910.documents.jpa.daoImpl;

import com.github.dimka9910.documents.dao.ConcreteDocumentDao;
import com.github.dimka9910.documents.dao.DocumentDao;
import com.github.dimka9910.documents.dto.files.catalogues.CatalogueDto;
import com.github.dimka9910.documents.dto.files.documents.ConcreteDocumentDto;
import com.github.dimka9910.documents.dto.files.documents.DocumentDto;
import com.github.dimka9910.documents.jpa.entity.files.catalogues.Catalogue;
import com.github.dimka9910.documents.jpa.entity.files.documents.ConcreteDocument;
import com.github.dimka9910.documents.jpa.entity.files.documents.Document;
import com.github.dimka9910.documents.jpa.entityParser.files.CatalogueParser;
import com.github.dimka9910.documents.jpa.entityParser.files.DocumentParser;
import com.github.dimka9910.documents.jpa.exceprions.IdNotFoundException;
import com.github.dimka9910.documents.jpa.repository.CatalogueRepository;
import com.github.dimka9910.documents.jpa.repository.ConcreteDocumentRepository;
import com.github.dimka9910.documents.jpa.repository.DocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Component
public class DocumentDaoJpa implements DocumentDao {
    @Autowired
    private CatalogueRepository catalogueRepository;
    @Autowired
    private DocumentRepository documentRepository;
    @Autowired
    private ConcreteDocumentRepository concreteDocumentRepository;
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
    @Transactional
    public DocumentDto addNewDocument(DocumentDto documentDto, CatalogueDto catalogueDto) {

        if (catalogueDto.getId() == null)
            throw new IdNotFoundException();

        documentDto.setParent_id(catalogueDto.getId());
        Document document = documentParser.DTOtoE(documentDto);

        em.persist(document);
        return documentParser.EtoDTO(document);
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
    @Transactional
    public Long deleteDocument(DocumentDto documentDto) {

        concreteDocumentRepository.getAllVersions(documentDto.getId())
                .forEach(em::remove);
        documentRepository.deleteById(documentDto.getId());
        return 0L;
    }
}
