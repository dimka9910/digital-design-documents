package com.github.dimka9910.documents.app;

import com.github.dimka9910.documents.dao.CatalogueDao;
import com.github.dimka9910.documents.dao.DocumentDao;
import com.github.dimka9910.documents.dto.files.documents.ConcreteDocumentDto;
import com.github.dimka9910.documents.dto.files.documents.DocumentDto;
import com.github.dimka9910.documents.dto.files.documents.FilePathDto;
import com.github.dimka9910.documents.jpa.entityParser.files.ConcreteDocumentParser;
import com.github.dimka9910.documents.jpa.entityParser.files.DocumentParser;
import com.github.dimka9910.documents.jpa.repository.CatalogueRepository;
import com.github.dimka9910.documents.jpa.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DocumentTest {
    @Autowired
    CatalogueDao catalogueDaoJpa;
    @Autowired
    DocumentDao documentDaoJpa;
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
//    @Rollback(false)
    public void test1() {
        DocumentDto documentDto = DocumentDto.builder()
                .priority("LOW")
                .documentType("fax")
                .userCreatedById(1L)
                .parentId(1L)
                .build();

        ConcreteDocumentDto concreteDocumentDto = ConcreteDocumentDto.builder()
                .name("newDoc")
                .description("descr")
                .userModifiedBy(1L)
                .version(1L)
                .build();

        List<FilePathDto> list = List.of(
                FilePathDto.builder().path("paaatthhhhh11").build(),
                FilePathDto.builder().path("paaatthhhhh12").build(),
                FilePathDto.builder().path("paaatthhhhh13").build()
        );

        concreteDocumentDto.setData(list);

        System.out.println(documentDaoJpa.addNewDocument(documentDto, concreteDocumentDto));
    }

    @Test
    @Transactional
    @Rollback(false)
    public void test2() {
        documentDaoJpa.deleteDocument(10L);
    }
}
