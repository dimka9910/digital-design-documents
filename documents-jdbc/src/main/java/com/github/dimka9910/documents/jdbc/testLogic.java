package com.github.dimka9910.documents.jdbc;

import com.github.dimka9910.documents.dao.DocumentTypeDao;
import com.github.dimka9910.documents.dto.files.catalogues.CatalogueDto;
import com.github.dimka9910.documents.dto.files.documents.DocumentTypeDto;
import com.github.dimka9910.documents.dto.user.UserDto;
import com.github.dimka9910.documents.dto.user.UserRolesEnum;
import com.github.dimka9910.documents.jdbc.implementation.CatalogueDaoImpl;
import com.github.dimka9910.documents.jdbc.implementation.DocumentDaoImpl;
import com.github.dimka9910.documents.jdbc.implementation.DocumentTypeDaoImpl;
import com.github.dimka9910.documents.jdbc.implementation.UserDaoImpl;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

@Slf4j
public class testLogic {

    public static void main(String[] args) {

////        UserDto user = UserDto.builder().login("l1").password("p2").role(UserRolesEnum.USER).build();
////        userDaoImpl.addNewUser(user);
//
//        UserDaoImpl userDaoImpl = new UserDaoImpl();
//        userDaoImpl.getAllUsers().forEach(System.out::println);
//        System.out.println(userDaoImpl.getCurrentUser());
//
//        DocumentTypeDaoImpl documentTypeDaoImpl = new DocumentTypeDaoImpl();
////        documentTypeDaoImpl.addNewDocumentType(DocumentTypeDto.builder().name("document").build());
//        System.out.println(documentTypeDaoImpl.getAllDocumentTypes());
//        System.out.println(documentTypeDaoImpl.getDocumentTypeByType("document"));
//
//        CatalogueDaoImpl catalogueDao = new CatalogueDaoImpl();
//        System.out.println(catalogueDao.getRootCatalogue());
//        System.out.println(catalogueDao.getAllCatalogues());
//        System.out.println(catalogueDao.getCatalogueById(1L));
//
//        DocumentDaoImpl documentDao = new DocumentDaoImpl();
//        System.out.println(documentDao.getAllDocuments());
//        System.out.println();
//
//
//
//        //catalogueDao.addCatalogue(CatalogueDto.builder().name("new2").build(), catalogueDao.getCatalogueById(1));
//        //catalogueDao.getAllChildren(catalogueDao.getCatalogueById(1L)).forEach(System.out::println);
//        System.out.println(catalogueDao.getCatalogueById(2L));
//        CatalogueDto catalogueDto = catalogueDao.getCatalogueById(2L);
//        catalogueDto.setName("ccc2");
//        catalogueDao.modifyCatalogue(catalogueDto);
//        System.out.println(catalogueDao.getCatalogueById(2L));
    }
}
