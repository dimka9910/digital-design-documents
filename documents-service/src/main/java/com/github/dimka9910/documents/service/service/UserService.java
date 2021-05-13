package com.github.dimka9910.documents.service.service;

import com.github.dimka9910.documents.dao.UserDao;
import com.github.dimka9910.documents.dto.user.UserDto;
import com.github.dimka9910.documents.jpa.exceprions.IdNotFoundException;
import com.github.dimka9910.documents.jpa.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    UserDao userDaoJpa;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return userRepository.findUserByLogin(s).orElseThrow(IdNotFoundException::new);
    }

    public UserDto addNewUser(UserDto userDto){
        return userDaoJpa.addNewUser(userDto);
    }

    public UserDto getCurrentUser(){
        return userDaoJpa.getCurrentUser();
    }

}
