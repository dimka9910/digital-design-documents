package com.github.dimka9910.documents.jpa.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Service {
    UserRepository userRepository;

    public Service(UserRepository userRepository1) {
        this.userRepository = userRepository1;
    }

    //    public void test(){
//        userRepository.getAll();
//    }
}
