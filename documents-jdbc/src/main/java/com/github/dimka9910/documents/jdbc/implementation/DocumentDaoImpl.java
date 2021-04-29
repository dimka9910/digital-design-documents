package com.github.dimka9910.documents.jdbc.implementation;

import com.github.dimka9910.documents.dao.DocumentDao;
import com.github.dimka9910.documents.dto.files.TypeOfFileEnum;
import com.github.dimka9910.documents.dto.files.catalogues.CatalogueDto;
import com.github.dimka9910.documents.dto.files.documents.ConcreteDocumentDto;
import com.github.dimka9910.documents.dto.files.documents.DocumentDto;
import com.github.dimka9910.documents.dto.files.documents.PriorityEnum;
import com.github.dimka9910.documents.jdbc.DbConnection;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.print.Doc;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;

@Slf4j
@Component("documentDaoImpl")
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
    public DocumentDto addNewDocument(DocumentDto documentDto, CatalogueDto catalogueDto) {
        String insert1 = "INSERT INTO DOCUMENT (name, priority, document_type_id, created_time, parent_id) " +
                "VALUES (?,?,?,?,?)";
        try (PreparedStatement preparedStatement = cn.prepareStatement(insert1, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, documentDto.getName());
            preparedStatement.setInt(2, documentDto.getPriority().ordinal());
            preparedStatement.setLong(3, documentDto.getDocumentType());
            preparedStatement.setTimestamp(4, getCurrentTime());
            preparedStatement.setLong(5, catalogueDto.getId());

            preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            if (rs.next()) {
                Long id = rs.getLong(1);
                return getDocumentById(id);
            } else {
                throw new SQLException("not inserted");
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        return null;
    }

    @Override
    public List<DocumentDto> getAllDocuments() {
        String stringQuery = "SELECT * FROM DOCUMENT";
        List<DocumentDto> list = List.of();
        try {
            list = getList(stringQuery, cn);
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        return list;
    }

    @Override
    public DocumentDto getDocumentById(Long id) {
        String stringQuery = "SELECT * FROM DOCUMENT WHERE id = ?";
        try (PreparedStatement statement = cn.prepareStatement(stringQuery)) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return parser(resultSet);
                }
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        return DocumentDto.builder().build();
    }

    @Override
    public DocumentDto modifyDocument(DocumentDto documentDto, ConcreteDocumentDto concreteDocumentDto) {
        String stringQuery = "UPDATE DOCUMENT SET name = ? WHERE id = ?";
        try (PreparedStatement statement = cn.prepareStatement(stringQuery, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, concreteDocumentDto.getName());
            statement.setLong(2, documentDto.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        return getDocumentById(documentDto.getId());
    }

    @Override
    public Long deleteDocument(DocumentDto documentDto) {
        String stringQuery = "DELETE FROM DOCUMENT WHERE id = ?";
        try (PreparedStatement statement = cn.prepareStatement(stringQuery)) {
            statement.setLong(1, documentDto.getId());
            return (long) statement.executeUpdate();
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        return 0L;
    }


}
