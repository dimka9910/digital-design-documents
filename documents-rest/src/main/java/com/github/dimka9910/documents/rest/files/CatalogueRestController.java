package com.github.dimka9910.documents.rest.files;

import com.github.dimka9910.documents.dto.files.FileAbstractDto;
import com.github.dimka9910.documents.dto.files.catalogues.CatalogueDto;
import com.github.dimka9910.documents.service.service.CatalogueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/catalogue")
public class CatalogueRestController {

    @Autowired
    CatalogueService catalogueService;

    @GetMapping("/{id}")
    public CatalogueDto getCatalogue(@PathVariable Long id){
        return catalogueService.getCatalogueById(id);
    }

    @GetMapping("/root")
    public CatalogueDto getRootCatalogue(){
        return catalogueService.getCatalogueById(null);
    }

    @GetMapping("/open/{id}")
    public List<FileAbstractDto> openCatalogue(@PathVariable Long id){
        return catalogueService.getInnerCataloguesAndDocuments(id);
    }

    @PostMapping(value = "",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public CatalogueDto addCatalogue(@RequestBody CatalogueDto catalogueDto){
        return catalogueService.createCatalogue(catalogueDto);
    }
}

