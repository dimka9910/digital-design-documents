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
        try (PreparedStatement statement = cn.prepareStatement(stringQuery)) {
            try (ResultSet catalogueResult = statement.executeQuery()) {
                if (catalogueResult.next()) {
                    return parser(catalogueResult);
                }
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        return CatalogueDto.builder().build();
    }

    @Override
    public CatalogueDto getCatalogueById(Long id) {
        String stringQuery = "SELECT * FROM CATALOGUE WHERE id = ?";
        try (PreparedStatement statement = cn.prepareStatement(stringQuery)) {
            statement.setLong(1, id);
            try (ResultSet catalogueResult = statement.executeQuery()) {
                if (catalogueResult.next()) {
                    return parser(catalogueResult);
                }
            }
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
            list = getAll(stringQuery, cn);
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        return list;
    }

    @Override
    public List<FileAbstractDto> getAllChildren(CatalogueDto catalogueDto) {
        String stringQueryCatalogue = "SELECT C.id as id, C.name as name, c.type_of_file as type_of_file, c.parent_id as parent_id\n" +
                "FROM CATALOGUE\n" +
                "JOIN CATALOGUE_AND_CATALOGUE CAC on CATALOGUE.id = CAC.CATALOGUE_id\n" +
                "JOIN CATALOGUE C on C.id = CAC.CATALOGUE_children_id WHERE CATALOGUE.id = '" + catalogueDto.getId() + "'";

        String stringQueryDocuments = "SELECT D.id as id, D.name as name, d.type_of_file as type_of_file,\n" +
                "       D.priority as priority, document_type_id, created_time, D.parent_id FROM CATALOGUE\n" +
                "JOIN CATALOGUE_AND_DOCUMENT CAD on CATALOGUE.id = CAD.CATALOGUE_id\n" +
                "JOIN DOCUMENT D on CAD.DOCUMENT_id = D.id WHERE CATALOGUE.id = '" + catalogueDto.getId() + "'";

        List<FileAbstractDto> list = new LinkedList<>();

        try {
            DocumentDaoImpl documentDao = new DocumentDaoImpl();
            list = getAll(stringQueryCatalogue, cn);
            list.addAll(documentDao.getAll(stringQueryDocuments, cn));
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        return list;
    }

    @Override
    public CatalogueDto addCatalogue(CatalogueDto catalogueDto, CatalogueDto parent) {

        String insert1 = "INSERT INTO CATALOGUE (name, parent_id) VALUES (?,?)";
        String insert2 = "INSERT INTO CATALOGUE_AND_CATALOGUE (CATALOGUE_id, CATALOGUE_children_id) VALUES (?,?)";
        try (PreparedStatement preparedStatement = cn.prepareStatement(insert1);
        PreparedStatement preparedStatement2 = cn.prepareStatement(insert2)){
            //cn.setAutoCommit(false);

            //Savepoint savepoint = cn.setSavepoint();

            preparedStatement.setString(1, catalogueDto.getName());
            preparedStatement.setLong(2, parent.getId());

            preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            if (rs.next()) {
                Long id = rs.getLong(1);
                System.out.println(id);
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
    public CatalogueDto modifyCatalogue(CatalogueDto catalogueDto) {
        String stringQuery = "UPDATE CATALOGUE SET name = ? WHERE id = ?";
        try (PreparedStatement statement = cn.prepareStatement(stringQuery)) {
            statement.setString(1, catalogueDto.getName());
            statement.setLong(2, catalogueDto.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        return catalogueDto;
    }
}
