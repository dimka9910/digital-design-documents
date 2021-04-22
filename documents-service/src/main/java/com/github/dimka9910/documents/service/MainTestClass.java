package com.github.dimka9910.documents.service;

import com.github.dimka9910.documents.dto.files.documents.ConcreteDocumentDto;
import com.github.dimka9910.documents.dto.files.documents.PriorityEnum;
import com.github.dimka9910.documents.service.service.CatalogueService;
import com.github.dimka9910.documents.service.service.DocumentService;

import java.util.List;

public class MainTestClass {

    public static void main(String[] args) {
        CatalogueService catalogueService = new CatalogueService();
        DocumentService documentService = new DocumentService();


        System.out.println();
        System.out.println(documentService.openDocumentById(1L));

        System.out.println();

//        documentService.saveNewDocument("new12345",
//                PriorityEnum.DEFAULT, 1L,
//                "descr", 1L, List.of(), catalogueService.getCatalogueById(2L));

        catalogueService.getInnerCataloguesAndDocuments(2L).forEach(System.out::println);

        System.out.println(documentService.openDocumentById(5L));

        ConcreteDocumentDto concreteDocumentDto = documentService.openDocumentById(5L);

        System.out.println(concreteDocumentDto);
        concreteDocumentDto.setDescription("new Descr");
        concreteDocumentDto.setVersion(2L);
        documentService.modifyDocument(concreteDocumentDto);

        System.out.println(documentService.openDocumentById(5L));

    }
}
