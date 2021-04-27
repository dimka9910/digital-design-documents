package com.github.dimka9910.documents.servlets.documents;

import com.github.dimka9910.documents.dto.files.catalogues.CatalogueDto;
import com.github.dimka9910.documents.dto.files.documents.ConcreteDocumentDto;
import com.github.dimka9910.documents.dto.files.documents.DocumentDto;
import com.github.dimka9910.documents.service.service.CatalogueService;
import com.github.dimka9910.documents.service.service.DocumentService;
import com.github.dimka9910.documents.servlets.DefaultMethods;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@WebServlet("/document")
@Slf4j
public class Document extends HttpServlet implements DefaultMethods {

    private Gson gson = new Gson();
    CatalogueService catalogueService = new CatalogueService();
    DocumentService documentService = new DocumentService();

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
        try {
            StringBuffer jb = getBody(request);
            DocumentDto documentDto = (DocumentDto) gson.fromJson(jb.toString(), DocumentDto.class);
            ConcreteDocumentDto concreteDocumentDto = (ConcreteDocumentDto) gson.fromJson(jb.toString(), ConcreteDocumentDto.class);
            concreteDocumentDto = documentService.saveNewDocument(documentDto, concreteDocumentDto);
            printData(gson, concreteDocumentDto, resp);
        } catch (JSONException e) {
            throw new IOException("Error parsing JSON request string");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse resp) throws ServletException, IOException {
        Long id = Long.valueOf(request.getParameter("id"));
        documentService.deleteDocumentById(id);
    }

}