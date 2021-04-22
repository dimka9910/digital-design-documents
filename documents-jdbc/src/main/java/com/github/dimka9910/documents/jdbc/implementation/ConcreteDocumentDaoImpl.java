package com.github.dimka9910.documents.jdbc.implementation;

import com.github.dimka9910.documents.dao.ConcreteDocumentDao;
import com.github.dimka9910.documents.dao.FilePathDao;
import com.github.dimka9910.documents.dto.files.TypeOfFileEnum;
import com.github.dimka9910.documents.dto.files.catalogues.CatalogueDto;
import com.github.dimka9910.documents.dto.files.documents.ConcreteDocumentDto;
import com.github.dimka9910.documents.dto.files.documents.DocumentDto;
import com.github.dimka9910.documents.jdbc.DbConnection;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
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
        String stringQuery = "INSERT INTO CONCRETE_DOCUMENT (name, description, version, modified_time, parent_id) " +
                "VALUES (?,?,?,?,?)";

        FilePathDaoImpl filePathDao = new FilePathDaoImpl();
        try (PreparedStatement statement = cn.prepareStatement(stringQuery)) {
            statement.setString(1, concreteDocumentDto.getName());
            statement.setString(2, concreteDocumentDto.getDescription());
            statement.setLong(3, concreteDocumentDto.getVersion());
            statement.setTimestamp(4, getCurrentTime());
            statement.setLong(5, documentDto.getId());
            statement.executeUpdate();
            ResultSet rs = statement.getGeneratedKeys();
            if (rs.next()) {
                Long id = rs.getLong(1);
                concreteDocumentDto.setId(id);
                if (concreteDocumentDto.getData() != null)
                    concreteDocumentDto.getData().forEach(v -> filePathDao.addNewFilePathOfConcreteDocument(v, concreteDocumentDto));
                return concreteDocumentDto;
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        return null;
    }

    @Override
    public ConcreteDocumentDto getLastVersion(DocumentDto documentDto) {

        String stringQuery = "SELECT C.id as id, C.name as name, description, version, modified_time, c.parent_id as parent_id " +
                "FROM DOCUMENT JOIN CONCRETE_DOCUMENT C on DOCUMENT.id = C.parent_id " +
                "WHERE C.parent_id = ? " +
                "ORDER BY version DESC";

        //ПОМЕНЯТЬ. В НОРМ БАЗЕ НЕТ ДОКОВ БЕЗ ДЕТЕЙ
        try {
            return (ConcreteDocumentDto) getOne(stringQuery, cn, documentDto.getId());
        } catch (Exception ignored) {

        }

        return ConcreteDocumentDto.builder().build();
    }

    @Override
    public List<ConcreteDocumentDto> getAllVersions(DocumentDto documentDto) {
        String stringQuery = "SELECT C.id as id, C.name as name, description, version, modified_time, c.parent_id as parent_id\n" +
                "FROM DOCUMENT JOIN CONCRETE_DOCUMENT C on DOCUMENT.id = C.parent_id\n" +
                "WHERE C.parent_id = " + documentDto.getId() +
                " ORDER BY version DESC";
        try {
            return getList(stringQuery, cn);
        } catch (SQLException exception) {
            log.error(exception.getMessage());
        }
        return List.of();
    }

    @Override
    public void deleteConcreteDocument(ConcreteDocumentDto concreteDocumentDto) {
        String stringQuery = "DELETE FROM CONCRETE_DOCUMENT WHERE id = ?";
        try (PreparedStatement statement = cn.prepareStatement(stringQuery)) {
            statement.setLong(1, concreteDocumentDto.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
    }

}
