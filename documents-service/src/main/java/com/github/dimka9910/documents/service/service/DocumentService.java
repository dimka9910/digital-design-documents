package com.github.dimka9910.documents.service.service;

import com.github.dimka9910.documents.dao.ConcreteDocumentDao;
import com.github.dimka9910.documents.dao.DocumentDao;
import com.github.dimka9910.documents.dao.DocumentTypeDao;
import com.github.dimka9910.documents.dto.files.catalogues.CatalogueDto;
import com.github.dimka9910.documents.dto.files.documents.ConcreteDocumentDto;
import com.github.dimka9910.documents.dto.files.documents.DocumentDto;
import com.github.dimka9910.documents.dto.files.documents.DocumentTypeDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DocumentService {

    @Autowired
    UserService userService;

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
        return concreteDocumentDao.getLastVersion(id);
    }

    public List<ConcreteDocumentDto> getAllVersionsById(Long id){
        return concreteDocumentDao.getAllVersions(id);
    }

    public DocumentDto saveNewDocument(DocumentDto documentDto, ConcreteDocumentDto concreteDocumentDto){
        documentDto.setUserCreatedById(userService.getCurrentUser().getId());
        return documentDao.addNewDocument(documentDto, concreteDocumentDto);
    }

    public List<ConcreteDocumentDto> getAllVersions(Long id){
        return concreteDocumentDao.getAllVersions(id);
    }


    public ConcreteDocumentDto modifyDocument(ConcreteDocumentDto concreteDocumentDto){
        concreteDocumentDao.addNewVersion(concreteDocumentDto);
        return concreteDocumentDto;
    }

    public List<DocumentTypeDto> getAllDocumentTypes(){
        return documentTypeDao.getAllDocumentTypes();
    }

    public DocumentTypeDto addDocumentType(String name){
        return documentTypeDao.addNewDocumentType(DocumentTypeDto.builder().name(name).build());
    }

    public void deleteDocumentById(Long id){
        documentDao.deleteDocument(id);
    }
}
