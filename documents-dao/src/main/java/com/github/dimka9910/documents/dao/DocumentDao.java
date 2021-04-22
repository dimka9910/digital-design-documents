package com.github.dimka9910.documents.dao;

import com.github.dimka9910.documents.dto.files.documents.DocumentDto;

import java.util.List;

public interface DocumentDao extends AbstractDao {
    List<DocumentDto> getAllDocuments();
}
