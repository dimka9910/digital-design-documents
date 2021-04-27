package com.github.dimka9910.documents.servlets.documents;


import com.github.dimka9910.documents.dto.files.catalogues.CatalogueDto;
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

@WebServlet("/document/open")
@Slf4j
public class DocumentOpen extends HttpServlet implements DefaultMethods {

    private Gson gson = new Gson();
    CatalogueService catalogueService = new CatalogueService();
    DocumentService documentService = new DocumentService();

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Long id = Long.valueOf(request.getParameter("id"));
            printData(gson, documentService.openDocumentById(id), resp);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }


}