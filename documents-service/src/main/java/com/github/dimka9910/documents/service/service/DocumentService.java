package com.github.dimka9910.documents.service.service;

import com.github.dimka9910.documents.dao.ConcreteDocumentDao;
import com.github.dimka9910.documents.dao.DocumentDao;
import com.github.dimka9910.documents.dao.DocumentTypeDao;
import com.github.dimka9910.documents.dto.files.catalogues.CatalogueDto;
import com.github.dimka9910.documents.dto.files.documents.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("documentService")
public class DocumentService {

    private final DocumentDao documentDao;
    private final ConcreteDocumentDao concreteDocumentDao;
    private final DocumentTypeDao documentTypeDao;

    public DocumentService(DaoFactory daoFactory) {
        concreteDocumentDao = daoFactory.getConcreteDocumentDao();
        documentDao = daoFactory.getDocumentDao();
        documentTypeDao = daoFactory.getDocumentTypeDao();
    }

    public DocumentDto getDocumentById(Long id){
        return documentDao.getDocumentById(id);
    }

    public ConcreteDocumentDto openDocumentById(Long id){
        return concreteDocumentDao.getLastVersion(documentDao.getDocumentById(id));
    }

    public List<ConcreteDocumentDto> getAllVersionsById(Long id){
        return concreteDocumentDao.getAllVersions(documentDao.getDocumentById(id));
    }

    public ConcreteDocumentDto saveNewDocument(DocumentDto documentDto, ConcreteDocumentDto concreteDocumentDto){

        CatalogueDto catalogueDto = CatalogueDto.builder().id(documentDto.getParent_id()).build();
        documentDto = documentDao.addNewDocument(documentDto, catalogueDto);

        return concreteDocumentDao.addNewVersion(documentDto, concreteDocumentDto);
    }

    public List<ConcreteDocumentDto> getAllVersions(DocumentDto documentDto){
        return concreteDocumentDao.getAllVersions(documentDto);
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
