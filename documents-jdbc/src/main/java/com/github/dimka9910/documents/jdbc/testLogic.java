package com.github.dimka9910.documents.jdbc;

import com.github.dimka9910.documents.dto.user.UserDto;
import com.github.dimka9910.documents.dto.user.UserRolesEnum;
import com.github.dimka9910.documents.jdbc.implementation.UserDaoImpl;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

@Slf4j
public class testLogic {

    public static void main(String[] args) {
        UserDto user = UserDto.builder().login("l1").password("p2").role(UserRolesEnum.USER).build();
        UserDaoImpl userDaoImpl = new UserDaoImpl();
        //userDaoImpl.addNewUser(user);

        userDaoImpl.getAllUsers().forEach(System.out::println);

        System.out.println(userDaoImpl.getCurrentUser());


    }
}
