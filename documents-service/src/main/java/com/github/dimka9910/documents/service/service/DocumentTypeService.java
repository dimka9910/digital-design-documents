package com.github.dimka9910.documents.service.service;

import com.github.dimka9910.documents.dao.DocumentDao;
import com.github.dimka9910.documents.dao.DocumentTypeDao;
import com.github.dimka9910.documents.dto.files.documents.DocumentTypeDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DocumentTypeService {

    @Autowired
    DocumentTypeDao documentTypeDao;

    public List<DocumentTypeDto> getAllDocumentTypes(){
        return documentTypeDao.getAllDocumentTypes();
    }

    public DocumentTypeDto addDocumentType(String name){
        return documentTypeDao.addNewDocumentType(DocumentTypeDto.builder().name(name).build());
    }

}
