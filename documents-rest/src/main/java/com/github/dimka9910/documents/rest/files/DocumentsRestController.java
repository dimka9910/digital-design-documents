package com.github.dimka9910.documents.rest.files;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/documents")
public class DocumentsRestController {


    @GetMapping(value = "/lol")
    public String getQuizzes() {
        return "hello";
    }
}
