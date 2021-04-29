package com.github.dimka9910.documents.servlets;

import com.google.gson.Gson;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

public interface DefaultMethods {

    default void printData(Gson gson, Object data, HttpServletResponse resp) throws IOException {
        String jsonObj = gson.toJson(data);
        PrintWriter out = resp.getWriter();
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        out.print(jsonObj);
        out.flush();
    }

    default StringBuffer getBody(HttpServletRequest request) throws IOException {
        StringBuffer jb = new StringBuffer();
        String line = null;
        BufferedReader reader = request.getReader();
        while ((line = reader.readLine()) != null)
            jb.append(line);
        return jb;
    }

    default String toJson(CloseableHttpResponse response) throws IOException {
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
}
