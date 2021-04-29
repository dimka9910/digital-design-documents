package com.github.dimka9910.documents.dao;

import com.github.dimka9910.documents.dto.files.catalogues.CatalogueDto;
import com.github.dimka9910.documents.dto.files.documents.ConcreteDocumentDto;
import com.github.dimka9910.documents.dto.files.documents.DocumentDto;

import java.util.List;

public interface ConcreteDocumentDao extends AbstractDao{
    ConcreteDocumentDto addNewVersion(DocumentDto documentDto, ConcreteDocumentDto concreteDocumentDto);
    ConcreteDocumentDto getLastVersion(DocumentDto documentDto);
    List<ConcreteDocumentDto> getAllVersions(DocumentDto documentDto);

    ConcreteDocumentDto getById(Long id);

    Long deleteConcreteDocument(ConcreteDocumentDto catalogueDto);
}
