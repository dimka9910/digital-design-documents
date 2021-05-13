package com.github.dimka9910.documents.dao;

import com.github.dimka9910.documents.dto.files.documents.ConcreteDocumentDto;
import com.github.dimka9910.documents.dto.files.documents.DocumentDto;

import java.util.List;

public interface ConcreteDocumentDao extends AbstractDao{

    ConcreteDocumentDto addNewVersion(ConcreteDocumentDto concreteDocumentDto);
    ConcreteDocumentDto getLastVersion(Long id);
    List<ConcreteDocumentDto> getAllVersions(Long id);
    ConcreteDocumentDto getById(Long id);
    Long deleteConcreteDocument(Long id);
}
