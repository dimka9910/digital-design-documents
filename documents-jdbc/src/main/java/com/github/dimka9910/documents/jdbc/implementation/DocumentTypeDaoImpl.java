package com.github.dimka9910.documents.jdbc.implementation;

import com.github.dimka9910.documents.dao.DocumentTypeDao;
import com.github.dimka9910.documents.dto.files.catalogues.CatalogueDto;
import com.github.dimka9910.documents.dto.files.documents.ConcreteDocumentDto;
import com.github.dimka9910.documents.dto.files.documents.DocumentTypeDto;
import com.github.dimka9910.documents.jdbc.DbConnection;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

@Slf4j
@Component("documentTypeDaoImpl")
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
    public DocumentTypeDto getDocumentTypeById(Long id) {
        String stringQuery = "SELECT * FROM document_type WHERE id = ?";
        try {
            return (DocumentTypeDto) getOne(stringQuery, cn, id);
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        return null;
    }

    @Override
    public List<DocumentTypeDto> getAllDocumentTypes() {
        String statement = "SELECT * FROM DOCUMENT_TYPE";
        List<DocumentTypeDto> list = new LinkedList<>();
        try {
            list = getList(statement, cn);
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        return list;
    }

    @Override
    public DocumentTypeDto addNewDocumentType(DocumentTypeDto documentTypeDto) {
        String insert = "INSERT INTO DOCUMENT_TYPE (name) VALUES (?)";
        try (PreparedStatement preparedStatement = cn.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, documentTypeDto.getName().toLowerCase());
            preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            if (rs.next()) {
                Long id = rs.getLong(1);
                return getDocumentTypeById(id);
            } else {
                throw new SQLException("id not found");
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        return null;
    }


    @Override
    public DocumentTypeDto getDocumentTypeByType(String name) {
        String insert = "SELECT * FROM DOCUMENT_TYPE WHERE name = ?";
        try (PreparedStatement statement = cn.prepareStatement(insert)) {
            statement.setString(1, name.toLowerCase());
            try (ResultSet usersResult = statement.executeQuery()) {
                if (usersResult.next()) {
                    return parser(usersResult);
                }
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        return null;
    }

    @Override
    public Long deleteDocumentType(DocumentTypeDto documentTypeDto) {
        String stringQuery = "DELETE FROM DOCUMENT_TYPE WHERE id = ?";
        try (PreparedStatement statement = cn.prepareStatement(stringQuery)) {
            statement.setLong(1, documentTypeDto.getId());
            return (long) statement.executeUpdate();
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        return 0L;
    }
}
