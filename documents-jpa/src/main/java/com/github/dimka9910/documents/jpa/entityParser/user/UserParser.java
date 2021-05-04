package com.github.dimka9910.documents.jpa.entityParser.user;

import com.github.dimka9910.documents.dto.user.UserDto;
import com.github.dimka9910.documents.dto.user.UserRolesEnum;
import com.github.dimka9910.documents.jpa.entity.user.User;

public class UserParser {

    public static UserDto EtoDTO(User user) {
        return UserDto.builder()
                .id(user.getId())
                .login(user.getLogin())
                .password(user.getPassword())
                .role(UserRolesEnum.values()[user.getRole().ordinal()])
                .build();
    }

    public static User DTOtoE(UserDto userDto){
        return new User(userDto.getId(), userDto.getLogin(), userDto.getPassword(),
                com.github.dimka9910.documents.jpa.entity.user.UserRolesEnum.values()[userDto.getRole().ordinal()]);
    }

}
