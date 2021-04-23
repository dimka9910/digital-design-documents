package com.github.dimka9910.documents.jdbc.implementation;

import com.github.dimka9910.documents.dao.FilePathDao;
import com.github.dimka9910.documents.dto.files.catalogues.CatalogueDto;
import com.github.dimka9910.documents.dto.files.documents.ConcreteDocumentDto;
import com.github.dimka9910.documents.dto.files.documents.DocumentTypeDto;
import com.github.dimka9910.documents.dto.files.documents.FilePathDto;
import com.github.dimka9910.documents.jdbc.DbConnection;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.List;

@Slf4j
public class FilePathDaoImpl implements FilePathDao, BasicRequests {
    Connection cn = DbConnection.getConnection();

    @Override
    public FilePathDto parser(ResultSet resultSet) throws SQLException {
        Long id = (long) resultSet.getInt("id");
        String filepath = resultSet.getString("filepath");
        Long parent_id = resultSet.getLong("parent_id");
        return FilePathDto.builder()
                .id(id)
                .path(filepath)
                .parent_id(parent_id)
                .build();
    }

    @Override
    public FilePathDto getById(Long id) {
        String stringQuery = "SELECT * FROM FILE_PATH WHERE ID = ?";
        try {
            return (FilePathDto) getOne(stringQuery, cn, id);
        } catch (SQLException e){
            log.error(e.getMessage());
        }
        return FilePathDto.builder().build();
    }

    @Override
    public List<FilePathDto> getAllFilePathOfConcreteDocument(ConcreteDocumentDto concreteDocumentDto) {
        String stringQuery = "SELECT id, filepath, parent_id\n" +
                "FROM FILE_PATH\n" +
                "JOIN CONCRETE_DOCUMENT CD on CD.id = FILE_PATH.parent_id " +
                "WHERE parent_id = ?";
        List<FilePathDto> list = List.of();
        try {
            list = getList(stringQuery, cn, concreteDocumentDto.getId());
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        return list;
    }

    @Override
    public FilePathDto addNewFilePathOfConcreteDocument(FilePathDto filePathDto, ConcreteDocumentDto parent) {
        String insert1 = "INSERT INTO FILE_PATH (filepath, parent_id) VALUES (?,?)";
        try (PreparedStatement preparedStatement1 = cn.prepareStatement(insert1)){
            preparedStatement1.setString(1, filePathDto.getPath());
            preparedStatement1.setLong(2, parent.getId());

            preparedStatement1.executeUpdate();
            ResultSet rs = preparedStatement1.getGeneratedKeys();
            if (rs.next()) {
                Long id = rs.getLong(1);
                return getById(id);
            } else {
                throw new SQLException("NOT ADDED");
            }
        } catch(SQLException e){
            log.error(e.getMessage());
        }
        return null;
    }

    @Override
    public void deleteFilePath(FilePathDto filePathDto) {
        String stringQuery = "DELETE FROM FILE_PATH WHERE id = ?";
        try (PreparedStatement statement = cn.prepareStatement(stringQuery)) {
            statement.setLong(1, filePathDto.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
    }
}
