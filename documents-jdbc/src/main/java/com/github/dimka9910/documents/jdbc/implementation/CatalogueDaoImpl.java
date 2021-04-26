package com.github.dimka9910.documents.jdbc.implementation;

import com.github.dimka9910.documents.dao.CatalogueDao;
import com.github.dimka9910.documents.dto.files.FileAbstractDto;
import com.github.dimka9910.documents.dto.files.TypeOfFileEnum;
import com.github.dimka9910.documents.dto.files.catalogues.CatalogueDto;
import com.github.dimka9910.documents.dto.files.documents.DocumentDto;
import com.github.dimka9910.documents.dto.files.documents.DocumentTypeDto;
import com.github.dimka9910.documents.jdbc.DbConnection;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Slf4j
public class CatalogueDaoImpl implements CatalogueDao, BasicRequests {
    Connection cn = DbConnection.getConnection();

    public CatalogueDto parser(ResultSet catalogueResult) throws SQLException {
        Long id = (long) catalogueResult.getInt("id");
        String name = catalogueResult.getString("name");
        Long parent_id = catalogueResult.getLong("parent_id");
        Timestamp created_time = catalogueResult.getTimestamp("created_time");
        TypeOfFileEnum type = TypeOfFileEnum.values()[catalogueResult.getInt("type_of_file")];
        return CatalogueDto.builder()
                .id(id)
                .parent_id(parent_id)
                .name(name)
                .typeOfFile(type)
                .created_time(created_time)
                .build();
    }

    @Override
    public CatalogueDto getRootCatalogue() {
        String stringQuery = "SELECT * FROM CATALOGUE WHERE parent_id = id";
        try {
            return (CatalogueDto) getOne(stringQuery, cn);
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        return CatalogueDto.builder().build();
    }

    @Override
    public CatalogueDto getCatalogueById(Long id) {
        String stringQuery = "SELECT * FROM CATALOGUE WHERE id = ?";
        try {
            return (CatalogueDto) getOne(stringQuery, cn, id);
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        return CatalogueDto.builder().build();
    }

    @Override
    public List<CatalogueDto> getAllCatalogues() {
        String stringQuery = "SELECT * FROM CATALOGUE";
        List<CatalogueDto> list = List.of();
        try {
            list = getList(stringQuery, cn, null);
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        return list;
    }

    @Override
    public List<FileAbstractDto> getAllChildren(CatalogueDto catalogueDto) {
        String stringQueryCatalogue = "SELECT C.id as id, C.name as name, c.type_of_file as type_of_file, c.created_time, c.parent_id as parent_id\n" +
                "FROM CATALOGUE\n" +
                "JOIN CATALOGUE C on CATALOGUE.id = C.parent_id WHERE CATALOGUE.id = ? ORDER BY C.created_time";

        String stringQueryDocuments = "SELECT D.id as id, D.name as name, d.type_of_file as type_of_file,\n" +
                "       D.priority as priority, document_type_id, D.created_time, D.parent_id FROM CATALOGUE\n" +
                "JOIN DOCUMENT D on CATALOGUE.id = D.parent_id WHERE CATALOGUE.id = ? ORDER BY D.created_time";

        List<FileAbstractDto> list = new LinkedList<>();
        try {
            DocumentDaoImpl documentDao = new DocumentDaoImpl();
            list = getList(stringQueryCatalogue, cn, catalogueDto.getId());
            list.addAll(documentDao.getList(stringQueryDocuments, cn, catalogueDto.getId()));
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        return list;
    }

    @Override
    public CatalogueDto addCatalogue(CatalogueDto catalogueDto, CatalogueDto parent) {

        String insert1 = "INSERT INTO CATALOGUE (name, parent_id, created_time) VALUES (?,?,?)";
        try (PreparedStatement preparedStatement = cn.prepareStatement(insert1, Statement.RETURN_GENERATED_KEYS)){
            preparedStatement.setString(1, catalogueDto.getName());
            preparedStatement.setLong(2, parent.getId());
            preparedStatement.setTimestamp(3, getCurrentTime());

            preparedStatement.execute();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            if (rs.next()) {
                Long id = rs.getLong(1);
                return getCatalogueById(id);
            } else {
                throw new SQLException("id not found");
            }
        } catch(SQLException e){
            log.error(e.getMessage());
        }
        return CatalogueDto.builder().id(0L).build();
    }

    @Override
    public CatalogueDto modifyCatalogue(CatalogueDto catalogueDto) {
        String stringQuery = "UPDATE CATALOGUE SET name = ? WHERE id = ?";
        try (PreparedStatement statement = cn.prepareStatement(stringQuery, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, catalogueDto.getName());
            statement.setLong(2, catalogueDto.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        return catalogueDto;
    }

    @Override
    public void deleteCatalogue(CatalogueDto catalogueDto) {
        String stringQuery = "DELETE FROM CATALOGUE WHERE id = ?";
        try (PreparedStatement statement = cn.prepareStatement(stringQuery)) {
            statement.setLong(1, catalogueDto.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
    }
}
