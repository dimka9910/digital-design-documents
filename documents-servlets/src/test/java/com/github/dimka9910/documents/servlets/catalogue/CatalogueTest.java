package com.github.dimka9910.documents.servlets.catalogue;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.dimka9910.documents.dto.files.catalogues.CatalogueDto;
import com.github.dimka9910.documents.service.service.CatalogueService;
import com.github.dimka9910.documents.servlets.DefaultMethods;
import com.google.gson.Gson;
import junit.framework.Assert;
import junit.framework.TestCase;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class CatalogueTest extends TestCase implements DefaultMethods {

    private Gson gson = new Gson();
    CloseableHttpClient httpClient = HttpClients.createDefault();
    private static String rootName = "root";
    ObjectMapper mapper = new ObjectMapper();

    @Test
    public void test() throws IOException {
        HttpGet get = new HttpGet("http://localhost:8080//catalogue?id=1");
        CloseableHttpResponse response = httpClient.execute(get);
        String res = toJson(response);
        System.out.println(res);
        CatalogueDto catalogueDto = (CatalogueDto) gson.fromJson(res, CatalogueDto.class);
        System.out.println(catalogueDto);
        Assert.assertTrue(catalogueDto.getName().equals(rootName));
    }

    @Test
    public void addAndDeleteCatalogue() throws IOException {
        HttpPost post = new HttpPost("http://localhost:8080//catalogue");

        Long parent_id = 1L;
        String name = "test_child";

        CatalogueDto catalogueDto = CatalogueDto.builder().parent_id(parent_id).name(name).build();
        StringEntity stringEntity = new StringEntity(mapper.writeValueAsString(catalogueDto), ContentType.APPLICATION_JSON);
        post.setEntity(stringEntity);
        CloseableHttpResponse response = httpClient.execute(post);
        String res = toJson(response);
        catalogueDto = (CatalogueDto) gson.fromJson(res, CatalogueDto.class);
        Assert.assertEquals(catalogueDto.getName(), name);

        HttpDelete delete = new HttpDelete("http://localhost:8080//catalogue?id=" + catalogueDto.getId());
        response = httpClient.execute(delete);
        Assert.assertEquals(200, response.getStatusLine().getStatusCode());
    }
}