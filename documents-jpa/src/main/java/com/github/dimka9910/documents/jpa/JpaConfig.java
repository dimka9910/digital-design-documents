package com.github.dimka9910.documents.jpa;


import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
//@PropertySource("classpath:persistence.properties")
@ComponentScan("com.github.dimka9910.documents.jpa")
@EnableAutoConfiguration
public class JpaConfig {

}

