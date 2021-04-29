package com.github.dimka9910.documents.servlets;

import com.github.dimka9910.documents.dao.DocumentTypeDao;
import com.github.dimka9910.documents.dto.files.documents.DocumentTypeDto;
import com.github.dimka9910.documents.service.service.CatalogueService;
import com.github.dimka9910.documents.service.service.DocumentService;
import com.github.dimka9910.documents.servlets.DefaultMethods;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.json.HTTP;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;

@WebServlet("/documentTypes")
@Slf4j
public class DocumentTypes extends HttpServlet implements DefaultMethods {

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
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        printData(gson, documentService.getAllDocumentTypes(), resp);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse resp) throws ServletException, IOException {
        try {
            StringBuffer jb = getBody(request);
            DocumentTypeDto documentTypeDto = (DocumentTypeDto) gson.fromJson(jb.toString(), DocumentTypeDto.class);
            documentTypeDto = documentService.addDocumentType(documentTypeDto.getName());
            printData(gson, documentTypeDto, resp);
        } catch (JSONException e) {
            throw new IOException("Error parsing JSON request string");
        }
    }
}
