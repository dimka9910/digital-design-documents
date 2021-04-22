package com.github.dimka9910.documents.service.service;

import com.github.dimka9910.documents.dao.*;
import com.github.dimka9910.documents.jdbc.implementation.*;
import lombok.Getter;


public class DaoFactory {

    public static DaoFactory daoFactory;
    @Getter
    private CatalogueDao catalogueDao;
    @Getter
    private ConcreteDocumentDao concreteDocumentDao;
    @Getter
    private DocumentDao documentDao;
    @Getter
    private DocumentTypeDao documentTypeDao;
    @Getter
    private FilePathDao filePathDao;
    @Getter
    private UserDao userDao;

    private DaoFactory() {
        this.catalogueDao = new CatalogueDaoImpl();
        this.concreteDocumentDao = new ConcreteDocumentDaoImpl();
        this.documentDao = new DocumentDaoImpl();
        this.documentTypeDao = new DocumentTypeDaoImpl();
        this.filePathDao = new FilePathDaoImpl();
        this.userDao = new UserDaoImpl();
    }

    public static DaoFactory getInstance(String str){
        if (daoFactory == null){
            daoFactory = new DaoFactory();
        }
        return daoFactory;
    }
}
