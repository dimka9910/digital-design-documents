package com.github.dimka9910.documents.service.testing;

import com.github.dimka9910.documents.dto.files.catalogues.CatalogueDto;
import com.github.dimka9910.documents.service.ServiceConfig;
import com.github.dimka9910.documents.service.service.CatalogueService;
import com.github.dimka9910.documents.service.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;


public class CatalogueTesting {
    static ApplicationContext ctx = new AnnotationConfigApplicationContext(ServiceConfig.class);
    static CatalogueService catalogueService = ctx.getBean(CatalogueService.class);
    static DocumentService documentService = ctx.getBean(DocumentService.class);

    public static void root() {
        CatalogueDto catalogueDto;
        System.out.println("root_catalogue");
        catalogueDto = catalogueService.getCatalogueById(null);
        System.out.println(catalogueDto);
        System.out.println();
    }

    public static void inner(Long number) {
        System.out.println();
        System.out.println("------- inner files ------");
        catalogueService.getInnerCataloguesAndDocuments(number)
                .forEach(System.out::println);
    }

    public static void modify() {
        System.out.println(catalogueService.getCatalogueById(4L));
        String tmp = catalogueService.getCatalogueById(4L).getName();
        catalogueService.modifyCatalogue(4L, "modc3");
        System.out.println(catalogueService.getCatalogueById(4L));
        catalogueService.modifyCatalogue(4L, tmp);
        System.out.println(catalogueService.getCatalogueById(4L));
    }

    public static void creation(Long n) {
        inner(n);
        CatalogueDto catalogueDto = catalogueService.createCatalogue(CatalogueDto.builder().name("new_name8").build(), n);
        inner(n);
//        System.out.println(catalogueDto);
//        catalogueService.deleteCatalogueById(catalogueDto.getId());
//        inner(n);
    }

    public static void main(String[] args) {
        root();
//        inner(1L);
//        modify();
       //creation(1L);

    }

}
