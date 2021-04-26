package com.github.dimka9910.documents.servlets.catalogue;

import com.github.dimka9910.documents.dto.files.catalogues.CatalogueDto;
import com.github.dimka9910.documents.dto.files.documents.DocumentTypeDto;
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
import javax.ws.rs.Path;
import java.io.IOException;

@WebServlet("/catalogue")
@Slf4j
public class Catalogue extends HttpServlet implements DefaultMethods {

    private Gson gson = new Gson();
    CatalogueService catalogueService = new CatalogueService();
    DocumentService documentService = new DocumentService();

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Long id = Long.valueOf(request.getParameter("id"));
            printData(gson, catalogueService.getCatalogueById(id), resp);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse resp) throws ServletException, IOException {
        try {
            StringBuffer jb = getBody(request);
            CatalogueDto catalogueDto = (CatalogueDto) gson.fromJson(jb.toString(), CatalogueDto.class);
            if (catalogueDto.getId() != null)
                catalogueDto = catalogueService.modifyCatalogue(catalogueDto.getId(), catalogueDto.getName());
            else
                catalogueDto = catalogueService.createCatalogue(catalogueDto, catalogueDto.getParent_id());
            printData(gson, catalogueDto, resp);
        } catch (JSONException e) {
            throw new IOException("Error parsing JSON request string");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse resp) throws ServletException, IOException {
        Long id = Long.valueOf(request.getParameter("id"));
        catalogueService.deleteCatalogueById(id);
    }

}
