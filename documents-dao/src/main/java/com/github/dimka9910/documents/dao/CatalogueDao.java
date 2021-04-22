package com.github.dimka9910.documents.dao;

import com.github.dimka9910.documents.dto.files.FileAbstractDto;
import com.github.dimka9910.documents.dto.files.catalogues.CatalogueDto;

import java.util.List;

public interface CatalogueDao extends AbstractDao{

    CatalogueDto getRootCatalogue();

    CatalogueDto getCatalogueById(Long id);
    List<CatalogueDto> getAllCatalogues();
    List<FileAbstractDto> getAllChildren(CatalogueDto catalogueDto);

    CatalogueDto addCatalogue(CatalogueDto catalogueDto, CatalogueDto parent);

    CatalogueDto modifyCatalogue(CatalogueDto catalogueDto);
}
