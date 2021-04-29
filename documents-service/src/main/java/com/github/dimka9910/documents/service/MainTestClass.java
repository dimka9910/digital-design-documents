package com.github.dimka9910.documents.service;

import com.github.dimka9910.documents.dto.files.documents.ConcreteDocumentDto;
import com.github.dimka9910.documents.dto.files.documents.PriorityEnum;
import com.github.dimka9910.documents.service.service.CatalogueService;
import com.github.dimka9910.documents.service.service.DocumentService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;

public class MainTestClass {

    public static void main(String[] args) {

        ApplicationContext context = new AnnotationConfigApplicationContext(ServiceConfig.class);

        CatalogueService catalogueService = context.getBean(CatalogueService.class);

//        System.out.println(catalogueService.modifyCatalogue(1L, "root1"));

//        System.out.println(catalogueService.getInnerCataloguesAndDocuments(1L));
        System.out.println(catalogueService.getInnerCataloguesAndDocuments(1L));

//        System.out.println(catalogueService.getCatalogueById(1L));
//        ApplicationContext context = ApplicationContextHolder.getContext();
//        context.getBean(CatalogueService.class);


//        System.out.println();
//        System.out.println(documentService.openDocumentById(1L));

//        System.out.println(catalogueService.getCatalogueById(1L));

//        System.out.println(catalogueService.getInnerCataloguesAndDocuments(1L));

//        System.out.println();

//        documentService.saveNewDocument("new12345",
//                PriorityEnum.DEFAULT, 1L,
//                "descr", 1L, List.of(), catalogueService.getCatalogueById(2L));

//        catalogueService.getInnerCataloguesAndDocuments(2L).forEach(System.out::println);
//
//        System.out.println(documentService.openDocumentById(5L));
//
//        ConcreteDocumentDto concreteDocumentDto = documentService.openDocumentById(5L);
//
//        System.out.println(concreteDocumentDto);
//        concreteDocumentDto.setDescription("new Descr");
//        concreteDocumentDto.setVersion(2L);
//        documentService.modifyDocument(concreteDocumentDto);
//
//        System.out.println(documentService.openDocumentById(5L));

    }
}
