package com.github.dimka9910.documents.servlets.catalogue;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.dimka9910.documents.dto.files.catalogues.CatalogueDto;
import com.github.dimka9910.documents.servlets.DefaultMethods;
import com.google.gson.Gson;
import junit.framework.Assert;
import junit.framework.TestCase;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class CatalogueChildrenTest extends TestCase implements DefaultMethods {

    private Gson gson = new Gson();
    CloseableHttpClient httpClient = HttpClients.createDefault();
    private static String rootName = "root";
    ObjectMapper mapper = new ObjectMapper();

    @Test
    public void test() throws IOException {
        HttpGet get = new HttpGet("http://localhost:8080//catalogue/children?id=1");
        CloseableHttpResponse response = httpClient.execute(get);
        String res = toJson(response);
        System.out.println(res);
        CatalogueDto catalogueDto = (CatalogueDto) gson.fromJson(res, CatalogueDto.class);
        System.out.println(catalogueDto);
        Assert.assertTrue(catalogueDto.getName().equals(rootName));
    }

}