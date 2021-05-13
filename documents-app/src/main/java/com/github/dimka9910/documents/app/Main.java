package com.github.dimka9910.documents.app;

import com.github.dimka9910.documents.jpa.JpaConfig;
import com.github.dimka9910.documents.rest.config.RestConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@Import({
        JpaConfig.class,
        RestConfig.class
})
@SpringBootApplication
public class Main {

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}
