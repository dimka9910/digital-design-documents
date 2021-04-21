package com.github.dimka9910.documents.dto.user;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDto {
    private Long id;
    private String login;
    private String password;
    private UserRolesEnum role;

}
