package com.github.dimka9910.documents.dao;

import com.github.dimka9910.documents.dto.files.FileAbstractDto;
import com.github.dimka9910.documents.dto.files.catalogues.CatalogueDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CatalogueDao extends AbstractDao{

    CatalogueDto getRootCatalogue();
    CatalogueDto getCatalogueById(Long id);
    Page<CatalogueDto> getAllCatalogues(Pageable pageable, String name);
    List<FileAbstractDto> getAllChildren(Long id);
    CatalogueDto addCatalogue(CatalogueDto catalogueDto);
    CatalogueDto modifyCatalogue(CatalogueDto catalogueDto);
    Long deleteCatalogue(Long id);
}
