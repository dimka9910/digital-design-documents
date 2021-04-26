package com.github.dimka9910.documents.servlets;

import com.google.gson.Gson;

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
}
