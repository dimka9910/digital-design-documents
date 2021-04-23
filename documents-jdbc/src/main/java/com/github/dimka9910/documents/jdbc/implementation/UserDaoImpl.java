package com.github.dimka9910.documents.jdbc.implementation;

import com.github.dimka9910.documents.dao.UserDao;
import com.github.dimka9910.documents.dto.files.documents.FilePathDto;
import com.github.dimka9910.documents.dto.user.UserDto;
import com.github.dimka9910.documents.dto.user.UserRolesEnum;
import com.github.dimka9910.documents.jdbc.DbConnection;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

@Slf4j
public class UserDaoImpl implements UserDao, BasicRequests {
    Connection cn = DbConnection.getConnection();

    @Override
    public Object parser(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getLong("id");
        String login = resultSet.getString("login");
        String password = resultSet.getString("password");
        int role = resultSet.getInt("role");
        return UserDto.builder()
                .id(id)
                .login(login)
                .password(password)
                .role(UserRolesEnum.values()[role])
                .build();
    }


    // ПОКА КОСТЫЛЬНО НО СТОИТ БЫ ПОМЕНЯТЬ
    @Override
    public UserDto getCurrentUser() {
        String stringQuery = "SELECT * FROM USER";
        try {
            return (UserDto) getOne(stringQuery, cn);
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        return UserDto.builder().build();
    }

    @Override
    public UserDto addNewUser(UserDto userDto) {
        String insert = "INSERT INTO USER (login, password, role) VALUES (?,?,?)";
        try (PreparedStatement preparedStatement = cn.prepareStatement(insert)) {
            preparedStatement.setString(1, userDto.getLogin());
            preparedStatement.setString(2, userDto.getPassword());
            preparedStatement.setInt(3, userDto.getRole().ordinal());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        return null;
    }

    @Override
    public List<UserDto> getAllUsers() {
        String stringQuery = "SELECT * FROM USER";
        List<UserDto> list = new LinkedList<>();
        try {
            return getList(stringQuery, cn);
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        return list;
    }

    @Override
    public void deleteUser(UserDto userDto) {
        String stringQuery = "DELETE FROM USER WHERE id = ?";
        try (PreparedStatement statement = cn.prepareStatement(stringQuery)) {
            statement.setLong(1, userDto.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
    }
}
