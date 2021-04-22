package com.github.dimka9910.documents.jdbc.implementation;

import com.github.dimka9910.documents.dto.files.documents.DocumentDto;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public interface BasicRequests<T> {
    T parser(ResultSet resultSet) throws SQLException;

    default List<T> getAll(String stringQuery, Connection cn) throws SQLException {
        List<T> list = new LinkedList<>();
        try (PreparedStatement statement = cn.prepareStatement(stringQuery)) {
            try (ResultSet result = statement.executeQuery()) {
                while (result.next()) {
                    list.add(parser(result));
                }
            }
        }
        return list;
    }

    default Timestamp getCurrentTime(){
        return new Timestamp(new Date().getTime());
    }
}
