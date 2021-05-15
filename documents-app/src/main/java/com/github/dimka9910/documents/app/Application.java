package com.github.dimka9910.documents.app;

import com.github.dimka9910.documents.jpa.JpaConfig;
import com.github.dimka9910.documents.rest.config.RestConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
