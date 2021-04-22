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
    public List<FilePathDto> getAllFilePathOfConcreteDocument(ConcreteDocumentDto concreteDocumentDto) {
        String stringQuery = "\n" +
                "SELECT id, filepath, parent_id\n" +
                "FROM FILE_PATH_AND_CONCRETE_DOCUMENT\n" +
                "         JOIN FILE_PATH FP on FP.id = FILE_PATH_AND_CONCRETE_DOCUMENT.FILE_PATH_id " +
                "WHERE CONCRETE_DOCUMENT_id = '" + concreteDocumentDto.getId() + "'";
        List<FilePathDto> list = List.of();
        try {
            list = getAll(stringQuery, cn);
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        return list;
    }

    @Override
    public FilePathDto addNewFilePathOfConcreteDocument(FilePathDto filePathDto, ConcreteDocumentDto parent) {
        String insert1 = "INSERT INTO FILE_PATH (filepath, parent_id) VALUES (?,?)";
        String insert2 = "INSERT INTO FILE_PATH_AND_CONCRETE_DOCUMENT (concrete_document_id, file_path_id) VALUES (?,?)";
        try (PreparedStatement preparedStatement1 = cn.prepareStatement(insert1);
             PreparedStatement preparedStatement2 = cn.prepareStatement(insert2)){
            //cn.setAutoCommit(false);
            //Savepoint savepoint = cn.setSavepoint();

            preparedStatement1.setString(1, filePathDto.getPath());
            preparedStatement1.setLong(2, parent.getId());

            preparedStatement1.executeUpdate();
            ResultSet rs = preparedStatement1.getGeneratedKeys();
            if (rs.next()) {
                Long id = rs.getLong(1);
                preparedStatement2.setLong(1, parent.getId());
                preparedStatement2.setLong(2, id);
                preparedStatement2.executeUpdate();
                //cn.commit();
            } else {
                //cn.rollback();
            }
            //cn.setAutoCommit(true);
        } catch(SQLException e){
//            if (cn != null) {
//                try {
//                    cn.rollback();
//                    cn.setAutoCommit(true);
//                } catch (SQLException e1){
//                    log.error(e1.getMessage());
//                }
//            }
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
