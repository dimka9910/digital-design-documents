package com.github.dimka9910.documents.servlets.catalogue;

import com.github.dimka9910.documents.service.service.CatalogueService;
import com.github.dimka9910.documents.service.service.DocumentService;
import com.github.dimka9910.documents.servlets.DefaultMethods;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/catalogue/children")
@Slf4j
public class CatalogueChildren extends HttpServlet implements DefaultMethods {

    private Gson gson = new Gson();
    CatalogueService catalogueService = new CatalogueService();

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
