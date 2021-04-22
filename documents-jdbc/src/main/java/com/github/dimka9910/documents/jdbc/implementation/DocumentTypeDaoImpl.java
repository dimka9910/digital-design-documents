package com.github.dimka9910.documents.jdbc.implementation;

import com.github.dimka9910.documents.dao.DocumentTypeDao;
import com.github.dimka9910.documents.dto.files.documents.ConcreteDocumentDto;
import com.github.dimka9910.documents.dto.files.documents.DocumentTypeDto;
import com.github.dimka9910.documents.jdbc.DbConnection;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

@Slf4j
public class DocumentTypeDaoImpl implements DocumentTypeDao, BasicRequests {
    Connection cn = DbConnection.getConnection();

    @Override
    public DocumentTypeDto parser(ResultSet resultSet) throws SQLException {
        Long id = (long)resultSet.getInt("id");
        String type = resultSet.getString("name");
        return DocumentTypeDto.builder()
                .id(id)
                .name(type)
                .build();
    }

    @Override
    public List<DocumentTypeDto> getAllDocumentTypes() {
        String statement = "SELECT * FROM DOCUMENT_TYPE";
        List<DocumentTypeDto> list = new LinkedList<>();
        try {
            list = getAll(statement, cn);
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        return list;
    }

    @Override
    public DocumentTypeDto addNewDocumentType(DocumentTypeDto documentTypeDto) {
        String insert = "INSERT INTO DOCUMENT_TYPE (name) VALUES (?)";
        try (PreparedStatement preparedStatement = cn.prepareStatement(insert)) {
            preparedStatement.setString(1, documentTypeDto.getName());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        return null;
    }


    @Override
    public DocumentTypeDto getDocumentTypeByType(String name) {
        String insert = "SELECT * FROM DOCUMENT_TYPE WHERE name = ?";
        try (PreparedStatement statement = cn.prepareStatement(insert)) {
            statement.setString(1, name);
            try (ResultSet usersResult = statement.executeQuery()) {
                if (usersResult.next()) {
                    return parser(usersResult);
                }
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        return DocumentTypeDto.builder().build();
    }

    @Override
    public void deleteDocumentType(DocumentTypeDto documentTypeDto) {
        String stringQuery = "DELETE FROM DOCUMENT_TYPE WHERE id = ?";
        try (PreparedStatement statement = cn.prepareStatement(stringQuery)) {
            statement.setLong(1, documentTypeDto.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
    }
}
