package com.github.dimka9910.documents.service.service;

import com.github.dimka9910.documents.dao.DocumentDao;
import com.github.dimka9910.documents.dao.DocumentTypeDao;
import com.github.dimka9910.documents.dto.files.documents.ConcreteDocumentDto;
import com.github.dimka9910.documents.dto.files.documents.DocumentDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class DocumentService {

    @Autowired
    UserService userService;
    @Autowired
    AccessService accessService;
    @Autowired
    DocumentDao documentDao;
    @Autowired
    DocumentTypeDao documentTypeDao;

    // Pageable request
    public Page<DocumentDto> getAllDocuments(Integer page, Integer pageSize, String name, String documentType) {
        Pageable paging = PageRequest.of(page, pageSize);
        Page<DocumentDto> pageOfDocuments = documentDao.getAllDocuments(paging, name, documentType);

        return pageOfDocuments;
    }

    public DocumentDto getDocumentById(Long id) {
        if (!accessService.chekRAccess(id))
            throw new AccessDeniedException("Access error");
        return documentDao.getDocumentById(id);
    }

    public List<ConcreteDocumentDto> getAllVersionsById(Long id) {
        if (!accessService.chekRAccess(id))
            throw new AccessDeniedException("Access error");
        return documentDao.getAllVersions(id);
    }

    public DocumentDto saveNewDocument(DocumentDto documentDto, ConcreteDocumentDto concreteDocumentDto) {
        if (!accessService.chekRWAccess(documentDto.getParentId()))
            throw new AccessDeniedException("Access error");
        documentDto.setUserCreatedById(userService.getCurrentUser().getId());
        concreteDocumentDto.setUserModifiedBy(userService.getCurrentUser().getId());
        return documentDao.addNewDocument(documentDto, concreteDocumentDto);
    }

    public DocumentDto modifyDocument(ConcreteDocumentDto concreteDocumentDto) {
        if (!accessService.chekRWAccess(concreteDocumentDto.getParentDocumentId()))
            throw new AccessDeniedException("Access error");
        concreteDocumentDto.setUserModifiedBy(userService.getCurrentUser().getId());
        return documentDao.modifyDocument(concreteDocumentDto);
    }

    public void deleteDocumentById(Long id) {
        if (!accessService.chekRWAccess(id))
            throw new AccessDeniedException("Access error");
        documentDao.deleteDocument(id);
    }
}
