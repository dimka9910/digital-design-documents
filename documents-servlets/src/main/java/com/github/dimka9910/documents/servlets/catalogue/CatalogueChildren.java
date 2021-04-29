package com.github.dimka9910.documents.servlets.catalogue;

import com.github.dimka9910.documents.service.service.CatalogueService;
import com.github.dimka9910.documents.service.service.DocumentService;
import com.github.dimka9910.documents.servlets.DefaultMethods;
import com.github.dimka9910.documents.servlets.TemporaryContextGetter;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/catalogue/children")
@Slf4j
@Component
public class CatalogueChildren extends HttpServlet implements DefaultMethods {

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
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Long id = Long.valueOf(req.getParameter("id"));
            printData(gson, catalogueService.getInnerCataloguesAndDocuments(id), resp);
        } catch (Exception e){
            log.error(e.getMessage());
        }
    }
}
