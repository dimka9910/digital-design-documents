package com.github.dimka9910.documents.jpa.entityParser.user;

import com.github.dimka9910.documents.dto.user.UserDto;
import com.github.dimka9910.documents.jpa.entity.user.User;
import com.github.dimka9910.documents.jpa.entity.user.UserRolesEnum;
import org.springframework.stereotype.Component;

@Component
public class UserParser {

    public UserDto EtoDTO(User user) {
        return UserDto.builder()
                .id(user.getId())
                .login(user.getLogin())
                .password(user.getPassword())
                .role(user.getRole().toString())
                .build();
    }

    public User DTOtoE(UserDto userDto){
        return new User(userDto.getId(), userDto.getLogin(), userDto.getPassword(), UserRolesEnum.USER);
    }

}
