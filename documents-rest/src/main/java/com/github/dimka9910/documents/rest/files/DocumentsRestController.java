package com.github.dimka9910.documents.rest.files;

import com.github.dimka9910.documents.dto.files.documents.ConcreteDocumentDto;
import com.github.dimka9910.documents.dto.files.documents.DocumentDto;
import com.github.dimka9910.documents.jpa.entity.files.documents.Document;
import com.github.dimka9910.documents.service.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/documents")
public class DocumentsRestController {

    @Autowired
    DocumentService documentService;

    @GetMapping(value = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public DocumentDto getDocument(@PathVariable Long id) {
        return documentService.getDocumentById(id);
    }

    @PostMapping(value = "/modify",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public DocumentDto modifyDocument(@RequestBody ConcreteDocumentDto concreteDocumentDto){
        return documentService.modifyDocument(concreteDocumentDto);
    }

    @PostMapping(value = "",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public DocumentDto addNewDocument(@RequestBody DocumentDto documentDto){
        return documentService.saveNewDocument(documentDto, documentDto.getTopVersionDocument());
    }

    @DeleteMapping("")
    public ResponseEntity deleteDocument(@RequestBody DocumentDto documentDto){
        documentService.deleteDocumentById(documentDto.getId());
        return ResponseEntity.ok("ok");
    }

}
