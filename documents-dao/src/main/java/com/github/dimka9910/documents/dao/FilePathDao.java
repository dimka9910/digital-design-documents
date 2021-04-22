package com.github.dimka9910.documents.dao;

import com.github.dimka9910.documents.dto.files.documents.ConcreteDocumentDto;
import com.github.dimka9910.documents.dto.files.documents.FilePathDto;

import java.util.List;

public interface FilePathDao extends AbstractDao{
    List<FilePathDto> getAllFilePathOfConcreteDocument(ConcreteDocumentDto concreteDocumentDto);
    FilePathDto addNewFilePathOfConcreteDocument(FilePathDto filePathDto, ConcreteDocumentDto concreteDocumentDto);
    void deleteFilePath (FilePathDto filePathDto);

    FilePathDto getById(Long id);
}
