package com.github.dimka9910.documents.app;

import com.github.dimka9910.documents.dto.files.documents.ConcreteDocumentDto;
import com.github.dimka9910.documents.dto.files.documents.DocumentDto;
import com.github.dimka9910.documents.jpa.daoImpl.CatalogueDaoJpa;
import com.github.dimka9910.documents.jpa.entity.files.catalogues.Catalogue;
import com.github.dimka9910.documents.jpa.entity.files.documents.ConcreteDocument;
import com.github.dimka9910.documents.jpa.entity.files.documents.Document;
import com.github.dimka9910.documents.jpa.entity.files.documents.FilePath;
import com.github.dimka9910.documents.jpa.entity.user.User;
import com.github.dimka9910.documents.jpa.entityParser.files.CatalogueParser;
import com.github.dimka9910.documents.jpa.entityParser.files.ConcreteDocumentParser;
import com.github.dimka9910.documents.jpa.entityParser.files.DocumentParser;
import com.github.dimka9910.documents.jpa.repository.CatalogueRepository;
import com.github.dimka9910.documents.jpa.repository.DocumentRepository;
import com.github.dimka9910.documents.jpa.repository.UserRepository;
import org.checkerframework.checker.units.qual.C;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import java.sql.Timestamp;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTest {

    @Autowired
    CatalogueDaoJpa catalogueDaoJpa;
    @Autowired
    private CatalogueRepository catalogueRepository;
    @Autowired
    private UserRepository userRepository;
    @PersistenceContext
    private EntityManager em;
    @Autowired
    DocumentParser documentParser;
    @Autowired
    ConcreteDocumentParser concreteDocumentParser;

    @Test
    @Transactional
    @Rollback(false)    //
    public void test1() {
        DocumentDto documentDto = DocumentDto.builder().documentType("fax").parentId(1L).priority("HIGH").build();
        Document document = documentParser.DTOtoE(documentDto);

        ConcreteDocumentDto concreteDocumentDto = ConcreteDocumentDto.builder().name("new").version(1L)
                .description("helloo").build();
        ConcreteDocument concreteDocument = concreteDocumentParser.DTOtoE(concreteDocumentDto);

        em.persist(concreteDocument);
        em.persist(document);

        document.setTopVersionDocument(concreteDocument);
        document.getConcreteDocuments().add(concreteDocument);
    }

    @Test
    @Transactional
//    @Rollback(false)    //
    public void test2() {
        Catalogue catalogue = new Catalogue();
        catalogue.setCreatedTime(new Date());
        catalogue.setName("root");
        em.persist(catalogue);
    }

}