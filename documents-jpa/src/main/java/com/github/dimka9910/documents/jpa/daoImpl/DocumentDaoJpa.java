package com.github.dimka9910.documents.jpa.daoImpl;

import com.github.dimka9910.documents.dao.DocumentDao;
import com.github.dimka9910.documents.dto.files.documents.ConcreteDocumentDto;
import com.github.dimka9910.documents.dto.files.documents.DocumentDto;
import com.github.dimka9910.documents.jpa.entity.files.documents.ConcreteDocument;
import com.github.dimka9910.documents.jpa.entity.files.documents.Document;
import com.github.dimka9910.documents.jpa.entity.files.documents.FilePath;
import com.github.dimka9910.documents.jpa.entityParser.files.ConcreteDocumentParser;
import com.github.dimka9910.documents.jpa.entityParser.files.DocumentParser;
import com.github.dimka9910.documents.jpa.entityParser.files.FilePathParser;
import com.github.dimka9910.documents.jpa.exceprions.IdNotFoundException;
import com.github.dimka9910.documents.jpa.repository.ConcreteDocumentRepository;
import com.github.dimka9910.documents.jpa.repository.DocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    // Pageable request
    @Override
    public Page<DocumentDto> getAllDocuments(Pageable paging, String name, String documentType) {
        if (name != null && documentType != null){
            name = "%" + name + "%";   // REGEXP
            return documentRepository.findAllDocumentsByNameAndType(name, documentType, paging).map(documentParser::EtoDTO);
        }
        if (name != null) {
            name = "%" + name + "%";   // REGEXP
            return documentRepository.findAllDocumentsByName(name, paging).map(documentParser::EtoDTO);
        } else if (documentType != null) {
            return documentRepository.findAllDocumentsByType(documentType.toLowerCase(), paging).map(documentParser::EtoDTO);
        }
        return documentRepository.findAllDocuments(paging).map(documentParser::EtoDTO);
    }



    @Override
    public DocumentDto getDocumentById(Long id) {
        Document document = documentRepository.findById(id).orElseThrow(IdNotFoundException::new);
        return documentParser.EtoDTO(document);
    }

    public DocumentDto addNewVersion(Document document, ConcreteDocumentDto concreteDocumentDto){

        ConcreteDocument concreteDocument = concreteDocumentParser.DTOtoE(concreteDocumentDto);

        concreteDocument.setParent(document);

        // ???????????????????? file path
        List<FilePath> list = List.of();

        if (concreteDocumentDto.getData() != null)
            list = concreteDocumentDto.getData().stream()
                    .map(filePathParser::DTOtoE)
                    .collect(Collectors.toList());


        document.getConcreteDocuments().add(concreteDocument);
        document.setTopVersionDocument(concreteDocument);

        em.persist(document);
        em.persist(concreteDocument);

        concreteDocument.getFilePathList().addAll(list);
        list.forEach(v ->{
            v.setParent(concreteDocument);
            em.persist(v);
        });
        return documentParser.EtoDTO(document);
    }

    @Override
    @Transactional
    public DocumentDto addNewDocument(DocumentDto documentDto, ConcreteDocumentDto concreteDocumentDto) {
        Document document = documentParser.DTOtoE(documentDto);
        concreteDocumentDto.setVersion(1L);
        return addNewVersion(document, concreteDocumentDto);
    }


    @Override
    public List<ConcreteDocumentDto> getAllVersions(Long id){
        return concreteDocumentParser.fromList(
                concreteDocumentRepository.getAllVersions(id)
        );
    }


    @Override
    @Transactional
    public DocumentDto modifyDocument(ConcreteDocumentDto concreteDocumentDto) {
        Document document = documentRepository.findById(concreteDocumentDto.getParentDocumentId())
                .orElseThrow(IdNotFoundException::new);
        concreteDocumentDto.setVersion(document.getTopVersionDocument().getVersion() + 1);
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
