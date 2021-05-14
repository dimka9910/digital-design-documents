package com.github.dimka9910.documents.rest.user;

import com.github.dimka9910.documents.dto.user.UserDto;
import com.github.dimka9910.documents.jpa.entity.user.User;
import com.github.dimka9910.documents.jpa.entity.user.UserRolesEnum;
import com.github.dimka9910.documents.service.service.AccessService;
import com.github.dimka9910.documents.service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserRestController {

    @Autowired
    UserService userService;
    @Autowired
    AccessService accessService;

    @PostMapping("/register")
    public UserDto register(@RequestBody @Valid UserDto userDto){
        return userService.addNewUser(userDto);
    }


    @GetMapping("/getcurrent")
    public UserDto getCurrentUser(){
        return userService.getCurrentUser();
    }

    @PostMapping("/grantaccess/{id}/{role}")
    public UserDto grantAccess(@PathVariable("id") Long id, @PathVariable("role") UserRolesEnum role){
        return accessService.grantAccess(id, role);
    }




}

