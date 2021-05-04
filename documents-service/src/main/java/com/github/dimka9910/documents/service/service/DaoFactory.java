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
    private CatalogueDao catalogueDao;
    @Autowired
    private ConcreteDocumentDao concreteDocumentDao;
    @Autowired
    private DocumentDao documentDao;
    @Autowired
    private DocumentTypeDao documentTypeDao;
    @Autowired
    private UserDao userDao;
}
