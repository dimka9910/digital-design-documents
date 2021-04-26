package com.github.dimka9910.documents.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.*;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDto {
    private Long id;
    @JsonProperty(access = Access.WRITE_ONLY)
    private String login;
    private String password;
    private UserRolesEnum role;

}
