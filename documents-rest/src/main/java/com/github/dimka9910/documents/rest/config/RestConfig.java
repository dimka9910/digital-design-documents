package com.github.dimka9910.documents.rest.config;


import com.github.dimka9910.documents.service.ServiceConfig;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

import java.security.Provider;

@Configuration
@ComponentScan("com.github.dimka9910.documents.rest")
@EnableAutoConfiguration
@Import({ServiceConfig.class})
public class RestConfig {

}
