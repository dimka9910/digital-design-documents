package com.github.dimka9910.documents.service.testing;

import com.github.dimka9910.documents.dao.FilePathDao;
import com.github.dimka9910.documents.dto.files.documents.ConcreteDocumentDto;
import com.github.dimka9910.documents.dto.files.documents.FilePathDto;
import com.github.dimka9910.documents.dto.files.documents.PriorityEnum;
import com.github.dimka9910.documents.service.service.CatalogueService;
import com.github.dimka9910.documents.service.service.DocumentService;

import java.util.ArrayList;
import java.util.List;

public class DocumentTesting {
    public static CatalogueService catalogueService = new CatalogueService();
    public static DocumentService documentService = new DocumentService();

    public static void getAllTypes(){
        documentService.getAllDocumentTypes().forEach(System.out::println);
    }

    public static void addNewDocument(){
        CatalogueTesting.inner(6L);

        System.out.println();
        System.out.println("new_document");
        List<FilePathDto> filePathDtos = new ArrayList<>();
        filePathDtos.add(FilePathDto.builder().path("pp").build());
        filePathDtos.add(FilePathDto.builder().path("pp2").build());

        ConcreteDocumentDto concreteDocumentDto = documentService.saveNewDocument("new1236",
                PriorityEnum.DEFAULT, 1L,
                "descr", 1L, filePathDtos, catalogueService.getCatalogueById(6L));

        System.out.println("--- concreteDocumentDto ---");
        System.out.println(documentService.openDocumentById(concreteDocumentDto.getParent_id()));
        System.out.println();

        concreteDocumentDto.setVersion(3L);
        concreteDocumentDto.setName("new_name");
        documentService.modifyDocument(concreteDocumentDto);

        System.out.println("--- concreteDocumentDto (mod) ---");
        System.out.println(documentService.openDocumentById(concreteDocumentDto.getParent_id()));


        System.out.println();
        documentService.getAllVersionsById(concreteDocumentDto.getParent_id()).forEach(System.out::println);

        //documentService.deleteDocumentById(concreteDocumentDto.getParent_id());

        CatalogueTesting.inner(1L);

    }


    public static void main(String[] args) {
        //getAllTypes();
        //CatalogueTesting.inner(4L);
        addNewDocument();

    }
}
