package com.github.dimka9910.documents.rest.files;

import com.github.dimka9910.documents.dto.files.documents.ConcreteDocumentDto;
import com.github.dimka9910.documents.dto.files.documents.DocumentDto;
import com.github.dimka9910.documents.service.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/documents")
public class DocumentsRestController {

    @Autowired
    DocumentService documentService;

    @GetMapping(value = "/{id}")
    public DocumentDto getDocument(@PathVariable Long id) {
        return documentService.getDocumentById(id);
    }

    @PostMapping("/modify")
    public DocumentDto modifyDocument(@RequestBody ConcreteDocumentDto concreteDocumentDto){
        return documentService.modifyDocument(concreteDocumentDto);
    }

    @PostMapping
    public DocumentDto addNewDocument(@RequestBody DocumentDto documentDto){
        return documentService.saveNewDocument(documentDto, documentDto.getTopVersionDocument());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteDocument(@PathVariable Long id){
        documentService.deleteDocumentById(id);
        return ResponseEntity.ok("ok");
    }

}
