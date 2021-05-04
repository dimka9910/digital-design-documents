package com.github.dimka9910.documents.jpa.entityParser.files;

import com.github.dimka9910.documents.dto.files.TypeOfFileEnum;
import com.github.dimka9910.documents.dto.files.catalogues.CatalogueDto;
import com.github.dimka9910.documents.dto.files.documents.DocumentDto;
import com.github.dimka9910.documents.dto.files.documents.PriorityEnum;
import com.github.dimka9910.documents.dto.user.UserRolesEnum;
import com.github.dimka9910.documents.jpa.entity.files.catalogues.Catalogue;
import com.github.dimka9910.documents.jpa.entity.files.documents.ConcreteDocument;
import com.github.dimka9910.documents.jpa.entity.files.documents.Document;
import com.github.dimka9910.documents.jpa.entity.files.documents.DocumentType;
import com.github.dimka9910.documents.jpa.entity.user.User;
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
    @PersistenceContext
    private EntityManager em;


    public DocumentDto EtoDTO(Document document) {
        return DocumentDto.builder()
                .id(document.getId())
                .created_time(new Timestamp(document.getCreated_time().getTime()))
                .parent_id(document.getParent_id().getId())
                .name(document.getName())
                .documentTypeName(document.getDocumentType().getName())
                .documentType(document.getDocumentType().getId())
                .priority(PriorityEnum.values()[document.getPriority().ordinal()])
                .typeOfFile(TypeOfFileEnum.DOCUMENT)
                .build();
    }

    public Document DTOtoE(DocumentDto documentDto) {

        DocumentType documentType;
        if (documentDto.getDocumentType() != null) {
            documentType = documentTypeRepository.findById(documentDto.getDocumentType()).orElse(null);
        }
        if (documentDto.getDocumentTypeName() != null) {
            documentType = documentTypeRepository.findByName(documentDto.getDocumentTypeName().toLowerCase()).orElse(null);
            if (documentType == null){
                em.persist(new DocumentType(documentDto.getDocumentTypeName().toLowerCase()));
            }
        }

        documentType = documentTypeRepository.findByName(documentDto.getDocumentTypeName().toLowerCase()).orElse(null);

        return new Document(documentDto.getId(),
                catalogueRepository.findById(documentDto.getParent_id()).orElse(null),
                new Date(),
                documentDto.getCreated_by() == null ? null : userRepository.findById(documentDto.getCreated_by()).orElse(null),
                documentDto.getName(),
                documentDto.getReadWritePermissionedUsers(),
                documentDto.getReadPermissionedUsers(),
                documentType,
                com.github.dimka9910.documents.jpa.entity.files.documents.PriorityEnum.values()[documentDto.getPriority().ordinal()]
                );
    }

    public List<DocumentDto> fromList(List<Document> list) {
        List<DocumentDto> documentDto = new LinkedList<>();
        list.forEach(v -> {
            documentDto.add(EtoDTO(v));
        });
        return documentDto;
    }


}
