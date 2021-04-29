package com.github.dimka9910.documents.servlets.documents;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.dimka9910.documents.dto.files.TypeOfFileEnum;
import com.github.dimka9910.documents.dto.files.catalogues.CatalogueDto;
import com.github.dimka9910.documents.dto.files.documents.DocumentDto;
import com.github.dimka9910.documents.dto.files.documents.DocumentVisualizationDto;
import com.github.dimka9910.documents.dto.files.documents.FilePathDto;
import com.github.dimka9910.documents.dto.files.documents.PriorityEnum;
import com.github.dimka9910.documents.jdbc.implementation.CatalogueDaoImpl;
import com.github.dimka9910.documents.service.ServiceConfig;
import com.github.dimka9910.documents.service.service.CatalogueService;
import com.github.dimka9910.documents.service.service.DocumentService;
import com.github.dimka9910.documents.servlets.DefaultMethods;
import com.github.dimka9910.documents.servlets.ServletsConfig;
import com.google.gson.Gson;
import junit.framework.TestCase;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ServletsConfig.class)
public class DocumentTest extends TestCase implements DefaultMethods {


    @Autowired
    private CatalogueService catalogueService;
    @Autowired
    private DocumentService documentService;

    ObjectMapper mapper = new ObjectMapper();


    private Gson gson = new Gson();
    CloseableHttpClient httpClient = HttpClients.createDefault();
    private static String rootName = "root";

    @Test
    public void test() throws IOException {
        assertTrue(catalogueService != null);

        DocumentDto documentDto1 = (DocumentDto) catalogueService.getInnerCataloguesAndDocuments(1L)
                .stream()
                .filter(v -> v.getTypeOfFile().equals(TypeOfFileEnum.DOCUMENT))
                .findAny().get();

        HttpGet get = new HttpGet("http://localhost:8080//document?id=" + documentDto1.getId());
        CloseableHttpResponse response = httpClient.execute(get);
        String res = toJson(response);
        DocumentDto documentDto = (DocumentDto) gson.fromJson(res, DocumentDto.class);

        Assert.assertEquals(documentDto, documentDto);
    }

    @Test
    public void addAndDeleteDocument() throws IOException {
        HttpPost post = new HttpPost("http://localhost:8080//document");

        List<FilePathDto> list = new ArrayList<>();
        list.add(FilePathDto.builder().path("path1").build());
        list.add(FilePathDto.builder().path("path2").build());
        list.add(FilePathDto.builder().path("path3").build());

        String name = "test_doc0090";
        Long documentType = 1L;
        String descr = "test_doc_descr";
        Long parentId = 1L;
        PriorityEnum priorityEnum = PriorityEnum.LOW;

        DocumentVisualizationDto document = DocumentVisualizationDto.builder()
                .name(name)
                .documentType(documentType)
                .description(descr)
                .data(list)
                .parent_id(parentId)
                .priority(priorityEnum)
                .build();

        StringEntity stringEntity = new StringEntity(mapper.writeValueAsString(document), ContentType.APPLICATION_JSON);
        post.setEntity(stringEntity);

        CloseableHttpResponse response = httpClient.execute(post);
        String res = toJson(response);

        DocumentDto documentDto = (DocumentDto) gson.fromJson(res, DocumentDto.class);
        Assert.assertEquals(document.getName(), documentDto.getName());
        Assert.assertEquals(document.getName(), documentDto.getTopVersionDocument().getName());
        Assert.assertEquals(document.getData().size(), documentDto.getTopVersionDocument().getData().size());
        Assert.assertEquals(Long.valueOf(1L), documentDto.getTopVersionDocument().getVersion());

        documentService.deleteDocumentById(documentDto.getId());
    }

}