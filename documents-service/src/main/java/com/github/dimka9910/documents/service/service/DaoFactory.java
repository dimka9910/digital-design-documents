package com.github.dimka9910.documents.service.service;

import com.github.dimka9910.documents.dao.*;
import com.github.dimka9910.documents.jdbc.implementation.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
@Data
public class DaoFactory {
    @Autowired
    @Qualifier("catalogueDaoImpl")
    private CatalogueDao catalogueDao;
    @Autowired
    @Qualifier("concreteDocumentDaoImpl")
    private ConcreteDocumentDao concreteDocumentDao;
    @Autowired
    @Qualifier("documentDaoImpl")
    private DocumentDao documentDao;
    @Autowired
    @Qualifier("documentTypeDaoImpl")
    private DocumentTypeDao documentTypeDao;
    @Autowired
    @Qualifier("filePathDaoImpl")
    private FilePathDao filePathDao;
    @Autowired
    @Qualifier("userDaoImpl")
    private UserDao userDao;
}
