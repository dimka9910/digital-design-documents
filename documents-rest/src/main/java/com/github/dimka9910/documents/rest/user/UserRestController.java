package com.github.dimka9910.documents.rest.user;

import com.github.dimka9910.documents.dto.user.UserDto;
import com.github.dimka9910.documents.jpa.entity.user.User;
import com.github.dimka9910.documents.jpa.entity.user.UserRolesEnum;
import com.github.dimka9910.documents.service.service.AccessService;
import com.github.dimka9910.documents.service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserRestController {

    @Autowired
    UserService userService;
    @Autowired
    AccessService accessService;
    @Autowired
    PasswordEncoder passwordEncoder;

    @PostMapping(value = "/register",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public UserDto register(@RequestBody @Valid UserDto userDto) {
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        return userService.addNewUser(userDto);
    }

    @GetMapping(value = "/current",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public UserDto getCurrentUser() {
        return userService.getCurrentUser();
    }

    @PostMapping(value = "/grantaccess",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public UserDto grantAccess(@RequestBody UserDto userDto) {
        return accessService.grantAccess(userDto.getId(), UserRolesEnum.valueOf(userDto.getRole()));
    }
}

