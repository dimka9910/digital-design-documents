package com.github.dimka9910.documents.jpa.entityParser.files;

import com.github.dimka9910.documents.dao.UserDao;
import com.github.dimka9910.documents.dto.files.documents.DocumentDto;
import com.github.dimka9910.documents.jpa.entity.files.documents.Document;
import com.github.dimka9910.documents.jpa.entity.files.documents.DocumentType;
import com.github.dimka9910.documents.jpa.entity.files.documents.PriorityEnum;
import com.github.dimka9910.documents.jpa.entity.user.User;
import com.github.dimka9910.documents.jpa.exceprions.IdNotFoundException;
import com.github.dimka9910.documents.jpa.repository.CatalogueRepository;
import com.github.dimka9910.documents.jpa.repository.DocumentRepository;
import com.github.dimka9910.documents.jpa.repository.DocumentTypeRepository;
import com.github.dimka9910.documents.jpa.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.sql.Timestamp;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Component
public class DocumentParser {

    @Autowired
    CatalogueRepository catalogueRepository;
    @Autowired
    DocumentRepository documentRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    DocumentTypeRepository documentTypeRepository;
    @Autowired
    UserDao userDaoJpa;
    @Autowired
    ConcreteDocumentParser concreteDocumentParser;
    @PersistenceContext
    private EntityManager em;


    public DocumentDto EtoDTO(Document document) {
        if (document == null)
            return null;

        return DocumentDto.builder()
                .id(document.getId())
                .createdTime(new Timestamp(document.getCreatedTime().getTime()))
                .parentId(document.getParentCatalogue().getId())
                .name("document.getName()")
                .documentType(document.getDocumentType().getName())
                .priority(document.getPriority().toString())
                .typeOfFile("DOCUMENT")
                .topVersionDocument(concreteDocumentParser.EtoDTO(document.getTopVersionDocument()))
                .build();
    }

    public Document DTOtoE(DocumentDto documentDto) {

        DocumentType documentType = null;
        if (documentDto.getDocumentType() != null) {
            documentType = documentTypeRepository.findByName(documentDto.getDocumentType().toLowerCase()).orElse(null);
            if (documentType == null) {
                em.persist(new DocumentType(documentDto.getDocumentType().toLowerCase()));
            }
            documentType = documentTypeRepository.findByName(documentDto.getDocumentType().toLowerCase())
                    .orElseThrow(IdNotFoundException::new);
        }


        User user = documentDto.getUserCreatedById() == null ?
                null :
                userRepository.findById(documentDto.getUserCreatedById()).orElseThrow(IdNotFoundException::new);


        Document document = new Document();
        document.setId(documentDto.getId());
        document.setPriority(PriorityEnum.valueOf(documentDto.getPriority()));
        document.setDocumentType(documentType);

        if (documentDto.getParentId() != null)
            document.setParentCatalogue(catalogueRepository.findById(documentDto.getParentId()).orElse(null));
        document.setUserCreatedBy(user);
        document.setCreatedTime(new Date());

        return document;
    }

    public List<DocumentDto> fromList(List<Document> list) {
        List<DocumentDto> documentDto = new LinkedList<>();
        list.forEach(v -> {
            v.setTopVersionDocument(null);
            documentDto.add(EtoDTO(v));
        });
        return documentDto;
    }
}
