package com.github.dimka9910.documents.service.testing;

import com.github.dimka9910.documents.dto.files.catalogues.CatalogueDto;
import com.github.dimka9910.documents.service.service.CatalogueService;
import com.github.dimka9910.documents.service.service.DocumentService;

public class CatalogueTesting {
    public static CatalogueService catalogueService = new CatalogueService();
    public static DocumentService documentService = new DocumentService();

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

    public static void creation() {
        inner(4L);
        CatalogueDto catalogueDto = catalogueService.createCatalogue(CatalogueDto.builder().name("new_name").build(), 4L);
        inner(4L);
        catalogueService.deleteCatalogueById(catalogueDto.getId());
        inner(4L);
    }

    public static void main(String[] args) {
//        root();
        inner(1L);
//        modify();
//       creation();

    }

}
