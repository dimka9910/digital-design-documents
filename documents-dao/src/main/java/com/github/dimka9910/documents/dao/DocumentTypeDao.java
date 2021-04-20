package com.github.dimka9910.documents.dao;

import com.github.dimka9910.documents.dto.files.documents.DocumentTypeDto;

import java.util.List;

public interface DocumentTypeDao extends AbstractDao{
    List<DocumentTypeDto> getAllDocumentTypes();
}
