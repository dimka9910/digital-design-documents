package com.github.dimka9910.documents.servlets.documents;

import com.github.dimka9910.documents.dto.files.catalogues.CatalogueDto;
import com.github.dimka9910.documents.servlets.DefaultMethods;
import com.google.gson.Gson;
import junit.framework.TestCase;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.jupiter.api.Test;


import java.io.IOException;
import java.io.InputStream;
import java.net.http.HttpClient;

public class DocumentTest extends TestCase implements DefaultMethods {

    private Gson gson = new Gson();
    CloseableHttpClient httpClient = HttpClients.createDefault();

    public String toJson(CloseableHttpResponse response) throws IOException {
        HttpEntity entity = response.getEntity();
        String res = "";
        if (entity != null) {
            InputStream instream = entity.getContent();
            byte[] bytes = IOUtils.toByteArray(instream);
            res = new String(bytes, "UTF-8");
            instream.close();
        }
        return res;
    }

    @Test
    public void test() throws IOException {
        HttpGet get = new HttpGet("http://localhost:8080//catalogue?id=1");
        CloseableHttpResponse response = httpClient.execute(get);
        String res = toJson(response);
        System.out.println(res);
        CatalogueDto catalogueDto = (CatalogueDto) gson.fromJson(res, CatalogueDto.class);
        System.out.println(catalogueDto);
    }

    @Test
    public void testPost() throws IOException {
        HttpGet get = new HttpGet("http://localhost:8080//catalogue?id=1");
        CloseableHttpResponse response = httpClient.execute(get);
        String res = toJson(response);
        System.out.println(res);
        CatalogueDto catalogueDto = (CatalogueDto) gson.fromJson(res, CatalogueDto.class);
        System.out.println(catalogueDto);
    }

}