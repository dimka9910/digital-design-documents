package com.github.dimka9910.documents.service.service;

import com.github.dimka9910.documents.dao.ConcreteDocumentDao;
import com.github.dimka9910.documents.dao.DocumentDao;
import com.github.dimka9910.documents.dao.DocumentTypeDao;
import com.github.dimka9910.documents.dto.files.catalogues.CatalogueDto;
import com.github.dimka9910.documents.dto.files.documents.*;

import java.util.List;

public class DocumentService {
    private DocumentDao documentDao = DaoFactory.getInstance("").getDocumentDao();
    private ConcreteDocumentDao concreteDocumentDao = DaoFactory.getInstance("").getConcreteDocumentDao();
    private DocumentTypeDao documentTypeDao = DaoFactory.getInstance("").getDocumentTypeDao();

    public DocumentDto getDocumentById(Long id){
        return documentDao.getDocumentById(id);
    }

    public ConcreteDocumentDto openDocumentById(Long id){
        return concreteDocumentDao.getLastVersion(documentDao.getDocumentById(id));
    }

    public List<ConcreteDocumentDto> getAllVersionsById(Long id){
        return concreteDocumentDao.getAllVersions(documentDao.getDocumentById(id));
    }



    public ConcreteDocumentDto saveNewDocument(String name,
            PriorityEnum priorityEnum,
            Long documentTypeId,
            String description,
            Long version,
            List<FilePathDto> listOfFilePath,
            CatalogueDto catalogueDto){

        DocumentDto documentDto = DocumentDto.builder()
                .name(name)
                .documentType(documentTypeId)
                .priority(priorityEnum)
                .parent_id(catalogueDto.getParent_id()).build();

        documentDto = documentDao.addNewDocument(documentDto, catalogueDto);

        ConcreteDocumentDto concreteDocumentDto = ConcreteDocumentDto.builder()
                .data(listOfFilePath)
                .version(version)
                .description(description)
                .name(name)
                .parent_id(documentDto.getId())
                .build();

        concreteDocumentDao.addNewVersion(documentDto, concreteDocumentDto);
        return concreteDocumentDto;
    }

    public ConcreteDocumentDto modifyDocument(ConcreteDocumentDto concreteDocumentDto){
        DocumentDto documentDto = DocumentDto.builder().id(concreteDocumentDto.getParent_id()).build();
        concreteDocumentDao.addNewVersion(documentDto, concreteDocumentDto);
        return concreteDocumentDto;
    }

    public List<DocumentTypeDto> getAllDocumentTypes(){
        return documentTypeDao.getAllDocumentTypes();
    }

    public DocumentTypeDto addDocumentType(String name){
        return documentTypeDao.addNewDocumentType(DocumentTypeDto.builder().name(name).build());
    }

    public void deleteDocumentById(Long id){
        documentDao.deleteDocument(documentDao.getDocumentById(id));
    }
}
