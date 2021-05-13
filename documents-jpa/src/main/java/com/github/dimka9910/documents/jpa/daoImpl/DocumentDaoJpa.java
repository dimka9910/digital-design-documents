package com.github.dimka9910.documents.jpa.daoImpl;

import com.github.dimka9910.documents.dao.DocumentDao;
import com.github.dimka9910.documents.dto.files.catalogues.CatalogueDto;
import com.github.dimka9910.documents.dto.files.documents.ConcreteDocumentDto;
import com.github.dimka9910.documents.dto.files.documents.DocumentDto;
import com.github.dimka9910.documents.jpa.entity.files.documents.ConcreteDocument;
import com.github.dimka9910.documents.jpa.entity.files.documents.Document;
import com.github.dimka9910.documents.jpa.entity.files.documents.FilePath;
import com.github.dimka9910.documents.jpa.entityParser.files.CatalogueParser;
import com.github.dimka9910.documents.jpa.entityParser.files.ConcreteDocumentParser;
import com.github.dimka9910.documents.jpa.entityParser.files.DocumentParser;
import com.github.dimka9910.documents.jpa.entityParser.files.FilePathParser;
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
import java.util.stream.Collectors;

@Component
public class DocumentDaoJpa implements DocumentDao {
    @Autowired
    private DocumentRepository documentRepository;
    @Autowired
    private ConcreteDocumentRepository concreteDocumentRepository;
    @Autowired
    private ConcreteDocumentParser concreteDocumentParser;
    @Autowired
    private FilePathParser filePathParser;
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

    @Transactional
    public DocumentDto addNewVersion(Document document, ConcreteDocumentDto concreteDocumentDto){

        ConcreteDocument concreteDocument = concreteDocumentParser.DTOtoE(concreteDocumentDto);

        em.persist(document);
        em.persist(concreteDocument);

        concreteDocument.setParent(document);

        // добавление file path
        List<FilePath> list = concreteDocumentDto.getData().stream()
                .map(filePathParser::DTOtoE)
                .collect(Collectors.toList());

        list.forEach(v ->{
                em.persist(v);
                v.setParent(concreteDocument);
        });

        concreteDocument.getFilePathList().addAll(list);

        document.getConcreteDocuments().add(concreteDocument);
        document.setTopVersionDocument(concreteDocument);
        return documentParser.EtoDTO(document);
    }

    @Override
    public DocumentDto addNewDocument(DocumentDto documentDto, ConcreteDocumentDto concreteDocumentDto) {
        Document document = documentParser.DTOtoE(documentDto);
        return addNewVersion(document, concreteDocumentDto);
    }

    @Override
    public DocumentDto modifyDocument(ConcreteDocumentDto concreteDocumentDto) {
        Document document = documentRepository.findById(concreteDocumentDto.getParentDocumentId())
                .orElseThrow(IdNotFoundException::new);

        return addNewVersion(document, concreteDocumentDto);
    }

    @Override
    @Transactional
    public Long deleteDocument(Long id) {
        concreteDocumentRepository.getAllVersions(id)
                .forEach(em::remove);
        documentRepository.deleteById(id);
        return 0L;
    }
}
