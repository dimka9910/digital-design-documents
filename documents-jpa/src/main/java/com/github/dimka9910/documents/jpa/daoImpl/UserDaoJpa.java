package com.github.dimka9910.documents.jpa.daoImpl;

import com.github.dimka9910.documents.dao.UserDao;
import com.github.dimka9910.documents.dto.user.UserDto;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserDaoJpa implements UserDao {

    @Override
    public UserDto getCurrentUser() {
        return null;
    }

    @Override
    public UserDto addNewUser(UserDto userDto) {
        return null;
    }

    @Override
    public List<UserDto> getAllUsers() {
        return null;
    }

    @Override
    public Long deleteUser(UserDto userDto) {
        return null;
    }
}
