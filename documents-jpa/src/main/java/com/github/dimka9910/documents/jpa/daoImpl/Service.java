package com.github.dimka9910.documents.jpa.daoImpl;

import com.github.dimka9910.documents.dao.CatalogueDao;
import com.github.dimka9910.documents.dao.DocumentDao;
import com.github.dimka9910.documents.dto.files.FileAbstractDto;
import com.github.dimka9910.documents.dto.files.TypeOfFileEnum;
import com.github.dimka9910.documents.dto.files.catalogues.CatalogueDto;
import com.github.dimka9910.documents.dto.files.documents.DocumentDto;
import com.github.dimka9910.documents.dto.files.documents.PriorityEnum;
import com.github.dimka9910.documents.jpa.entity.files.FileAbstract;
import com.github.dimka9910.documents.jpa.entity.files.catalogues.Catalogue;
import com.github.dimka9910.documents.jpa.entity.files.documents.Document;
import com.github.dimka9910.documents.jpa.entity.user.User;
import com.github.dimka9910.documents.jpa.entity.user.UserRolesEnum;
import com.github.dimka9910.documents.jpa.exceprions.IdNotFoundException;
import com.github.dimka9910.documents.jpa.repository.CatalogueRepository;
import com.github.dimka9910.documents.jpa.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Component("service")
@Slf4j
public class Service {

    @Autowired
    UserRepository userRepository;

    @Autowired
    CatalogueRepository catalogueRepository;

    @Autowired
    DocumentDao documentDaoJpa;

    @Autowired
    CatalogueDao catalogueDaoJpa;

    @PersistenceContext
    private EntityManager em;

    @Autowired

//    @Transactional
    public void test() {
//        DocumentDto documentDto = DocumentDto.builder().priority(PriorityEnum.LOW).documentTypeName("fax").parent_id(1L)
//                .typeOfFile(TypeOfFileEnum.DOCUMENT)
//                .name("nametest8").build();
//
//        documentDaoJpa.addNewDocument(documentDto, CatalogueDto.builder().id(1L).build());

//        documentDaoJpa.deleteDocument(DocumentDto.builder().id(26L).build());
//        catalogueDaoJpa.deleteCatalogue(CatalogueDto.builder().id(16L).build());

    }
}
