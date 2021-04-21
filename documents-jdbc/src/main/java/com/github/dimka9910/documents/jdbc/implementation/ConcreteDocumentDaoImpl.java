package com.github.dimka9910.documents.jdbc.implementation;

import com.github.dimka9910.documents.dao.ConcreteDocumentDao;
import com.github.dimka9910.documents.dto.files.TypeOfFileEnum;
import com.github.dimka9910.documents.dto.files.catalogues.CatalogueDto;
import com.github.dimka9910.documents.dto.files.documents.ConcreteDocumentDto;
import com.github.dimka9910.documents.dto.files.documents.DocumentDto;
import com.github.dimka9910.documents.jdbc.DbConnection;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

@Slf4j
public class ConcreteDocumentDaoImpl implements ConcreteDocumentDao, BasicRequests {
    Connection cn = DbConnection.getConnection();

    @Override
    public ConcreteDocumentDto parser(ResultSet resultSet) throws SQLException {
        Long id = (long) resultSet.getInt("id");
        String name = resultSet.getString("name");
        String description = resultSet.getString("description");
        Long version = resultSet.getLong("version");
        Timestamp modified_time = resultSet.getTimestamp("modified_time");
        Long parent_id = resultSet.getLong("parent_id");
        return ConcreteDocumentDto.builder()
                .id(id)
                .name(name)
                .description(description)
                .version(version)
                .modified_time(modified_time)
                .parent_id(parent_id)
                .build();
    }

    @Override
    public ConcreteDocumentDto addNewVersion(DocumentDto documentDto, ConcreteDocumentDto concreteDocumentDto) {
        return null;
    }

    @Override
    public ConcreteDocumentDto getLastVersion(DocumentDto documentDto) {
        return null;
    }

    @Override
    public List<ConcreteDocumentDto> getAllVersions(DocumentDto documentDto) {
        return null;
    }
}
