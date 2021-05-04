package com.github.dimka9910.documents.jpa.daoImpl;

import com.github.dimka9910.documents.dto.files.FileAbstractDto;
import com.github.dimka9910.documents.dto.files.catalogues.CatalogueDto;
import com.github.dimka9910.documents.jpa.entity.files.FileAbstract;
import com.github.dimka9910.documents.jpa.entity.files.catalogues.Catalogue;
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
@Transactional
public class Service {

    @Autowired
    UserRepository userRepository;

    @Autowired
    CatalogueRepository catalogueRepository;

    @Autowired
    CatalogueDaoJpa catalogueDaoJpa;

    @PersistenceContext
    private EntityManager em;

    @Autowired

//    @Transactional
    public void test() {

        List<Catalogue> list = catalogueRepository.getChildrens(1L);
        //System.out.println(catalogueRepository.getChildrens(1L).size());
        System.out.println(list.size());

//        list.forEach();
        //System.out.println(list);

        List<FileAbstractDto> list1 = catalogueDaoJpa.getAllChildren(CatalogueDto.builder().id(1L).build());
        System.out.println("----------");
        System.out.println(list1.size());
        list1.forEach(System.out::println);
//        try {
//        List<FileAbstractDto> list = catalogueDaoJpa.getAllChildren(CatalogueDto.builder().id(1L).build());
//
//        System.out.println(list.size());
//        list.forEach(System.out::println);

//        Catalogue catalogue = catalogueRepository.getById(9L).orElse(null);
//        catalogue.setName("mod");
////        System.out.println(catalogueRepository.save(catalogue));
//
//        em.merge(catalogue);

//            User user = userRepository.getById(1L).orElseThrow(IdNotFoundException::new);
//            Catalogue catalogue1 = catalogueRepository.getRoot().orElseThrow(IdNotFoundException::new);
//
//            System.out.println(user);
//
//            Catalogue catalogue = new Catalogue();
//            catalogue.setName("newCatalogue");
//            catalogue.setCreated_time(new Date());
//            catalogue.setParent_id(catalogue1);
//            catalogue.setCreated_by(user);
//
//            List<Long> listRW = new LinkedList<>();
//            listRW.add(user.getId());
//            List<Long> listR = new LinkedList<>();
//            listR.add(user.getId());
//
//            catalogue.setReadPermissionUsers(listR);
//            catalogue.setReadWritePermissionUsers(listRW);
//
//            catalogueRepository.save(catalogue);

//        } catch (Exception e) {
//            log.error(e.getMessage());
//        }
        //userRepository.findAll().forEach(System.out::println);

    }
}
