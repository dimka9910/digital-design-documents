package com.github.dimka9910.documents.dao;

import com.github.dimka9910.documents.dto.files.catalogues.CatalogueDto;
import com.github.dimka9910.documents.dto.files.documents.DocumentDto;

import java.util.List;

public interface DocumentDao extends AbstractDao {
    List<DocumentDto> getAllDocuments();

    DocumentDto getDocumentById(Long id);

    DocumentDto addNewDocument(DocumentDto documentDto, CatalogueDto catalogueDto);

    void deleteDocument(DocumentDto documentDto);
}
