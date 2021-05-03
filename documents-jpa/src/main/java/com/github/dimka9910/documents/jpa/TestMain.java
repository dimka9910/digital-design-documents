package com.github.dimka9910.documents.jpa;

import com.github.dimka9910.documents.jpa.repository.Service;
//import com.github.dimka9910.documents.jpa.repository.UserRepository;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class TestMain {

    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(JpaConfig.class);
        //context.getBean("userRepository", UserRepository.class);
//        service.test();
    }
}
