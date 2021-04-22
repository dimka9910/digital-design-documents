package com.github.dimka9910.documents.jdbc.implementation;

import com.github.dimka9910.documents.dao.DocumentDao;
import com.github.dimka9910.documents.dto.files.TypeOfFileEnum;
import com.github.dimka9910.documents.dto.files.catalogues.CatalogueDto;
import com.github.dimka9910.documents.dto.files.documents.DocumentDto;
import com.github.dimka9910.documents.dto.files.documents.PriorityEnum;
import com.github.dimka9910.documents.jdbc.DbConnection;
import lombok.extern.slf4j.Slf4j;

import javax.print.Doc;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;

@Slf4j
public class DocumentDaoImpl implements DocumentDao, BasicRequests {
    Connection cn = DbConnection.getConnection();

    public DocumentDto parser(ResultSet catalogueResult) throws SQLException {

        Long id = (long) catalogueResult.getInt("id");
        String name = catalogueResult.getString("name");
        TypeOfFileEnum type = TypeOfFileEnum.values()[catalogueResult.getInt("type_of_file")];
        PriorityEnum priority = PriorityEnum.values()[catalogueResult.getInt("priority")];
        Long document_type_id = catalogueResult.getLong("document_type_id");
        Timestamp created_time = catalogueResult.getTimestamp("created_time");
        Long parent_id = catalogueResult.getLong("parent_id");

        return DocumentDto.builder()
                .id(id)
                .name(name)
                .typeOfFile(type)
                .priority(priority)
                .documentType(document_type_id)
                .created_time(created_time)
                .parent_id(parent_id)
                .build();
    }

    @Override
    public List<DocumentDto> getAllDocuments() {
        String stringQuery = "SELECT * FROM DOCUMENT";
        List<DocumentDto> list = List.of();
        try {
            list = getAll(stringQuery, cn);
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        return list;
    }
}
