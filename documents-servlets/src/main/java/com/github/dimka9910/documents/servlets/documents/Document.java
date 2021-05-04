package com.github.dimka9910.documents.servlets.documents;

import com.github.dimka9910.documents.dto.files.catalogues.CatalogueDto;
import com.github.dimka9910.documents.dto.files.documents.ConcreteDocumentDto;
import com.github.dimka9910.documents.dto.files.documents.DocumentDto;
import com.github.dimka9910.documents.service.service.CatalogueService;
import com.github.dimka9910.documents.service.service.DocumentService;
import com.github.dimka9910.documents.servlets.DefaultMethods;
import com.github.dimka9910.documents.servlets.TemporaryContextGetter;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@WebServlet("/document")
@Slf4j
@Component
public class Document extends HttpServlet implements DefaultMethods {

    private Gson gson;
    CatalogueService catalogueService;
    DocumentService documentService;
    @Override
    public void init() throws ServletException {
        gson = new Gson();
        catalogueService = TemporaryContextGetter.getContext().getBean(CatalogueService.class);
        documentService = TemporaryContextGetter.getContext().getBean(DocumentService.class);
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Long id = Long.valueOf(request.getParameter("id"));
            printData(gson, documentService.getDocumentById(id), resp);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse resp) throws ServletException, IOException {
        log.info("------ concreteDocumentDto.getData().size()");

        StringBuffer jb = getBody(request);
        DocumentDto documentDto = (DocumentDto) gson.fromJson(jb.toString(), DocumentDto.class);
        ConcreteDocumentDto concreteDocumentDto = (ConcreteDocumentDto) gson.fromJson(jb.toString(), ConcreteDocumentDto.class);
        concreteDocumentDto = documentService.saveNewDocument(documentDto, concreteDocumentDto);
        documentDto.setTopVersionDocument(concreteDocumentDto);
        documentDto.setId(concreteDocumentDto.getParent_id());
        printData(gson, documentDto, resp);
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse resp) throws ServletException, IOException {
        Long id = Long.valueOf(request.getParameter("id"));
        documentService.deleteDocumentById(id);
    }

}