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
public class UserDaoImpl implements UserDao {
    Connection cn = DbConnection.getConnection();

    @Override
    public UserDto getCurrentUser() {
        try (Statement statement = cn.createStatement()) {
            try (ResultSet usersResult = statement.executeQuery("SELECT * FROM USER")) {
                while (usersResult.next()) {
                    Long id = (long)usersResult.getInt("id");
                    String login = usersResult.getString("login");
                    String password = usersResult.getString("password");
                    int role = usersResult.getInt("role");
                    return UserDto.builder()
                            .id(id)
                            .login(login)
                            .password(password)
                            .role(UserRolesEnum.values()[role])
                            .build();
                }
            } catch (SQLException e) {
                log.error(e.getMessage());
            }
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
        List<UserDto> list = new LinkedList<>();
        try (Statement statement = cn.createStatement()) {
            try (ResultSet usersResult = statement.executeQuery("SELECT * FROM USER")) {
                while (usersResult.next()) {
                    Long id = (long)usersResult.getInt("id");
                    String login = usersResult.getString("login");
                    String password = usersResult.getString("password");
                    int role = usersResult.getInt("role");
                    list.add(UserDto.builder()
                            .id(id)
                            .login(login)
                            .password(password)
                            .role(UserRolesEnum.values()[role])
                            .build());
                }
            } catch (SQLException e) {
                log.error(e.getMessage());
            }
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
