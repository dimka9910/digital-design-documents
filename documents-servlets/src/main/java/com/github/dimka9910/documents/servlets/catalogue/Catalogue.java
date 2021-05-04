package com.github.dimka9910.documents.servlets.catalogue;

import com.github.dimka9910.documents.dto.files.catalogues.CatalogueDto;
import com.github.dimka9910.documents.dto.files.documents.DocumentTypeDto;
import com.github.dimka9910.documents.service.service.CatalogueService;
import com.github.dimka9910.documents.service.service.DocumentService;
import com.github.dimka9910.documents.servlets.ApplicationContextHolder;
import com.github.dimka9910.documents.servlets.DefaultMethods;
import com.github.dimka9910.documents.servlets.TemporaryContextGetter;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.Path;
import java.io.IOException;
import java.util.Optional;

@WebServlet("/catalogue")
@Slf4j
@Component
public class Catalogue extends HttpServlet implements DefaultMethods {

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
        Long id = Long.valueOf(request.getParameter("id"));
        CatalogueDto catalogueDto = catalogueService.getCatalogueById(id);
        if (catalogueDto == null)
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        else
            printData(gson, catalogueDto, resp);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse resp) throws ServletException, IOException {
        StringBuffer jb = getBody(request);
        CatalogueDto catalogueDto = (CatalogueDto) gson.fromJson(jb.toString(), CatalogueDto.class);
        if (catalogueDto.getId() != null)
            catalogueDto = catalogueService.modifyCatalogue(catalogueDto.getId(), catalogueDto.getName());
        else
            catalogueDto = catalogueService.createCatalogue(catalogueDto, catalogueDto.getParent_id());

        printData(gson, catalogueDto, resp);
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse resp) throws ServletException, IOException {
        Long id = Long.valueOf(request.getParameter("id"));
        catalogueService.deleteCatalogueById(id);
    }

}
