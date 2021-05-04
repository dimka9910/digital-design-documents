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

        catalogueService.getInnerCataloguesAndDocuments(1L).forEach(System.out::println);

//        System.out.println(catalogueService.getCatalogueById(null));

    }
}
