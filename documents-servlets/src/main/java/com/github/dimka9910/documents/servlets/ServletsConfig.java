package com.github.dimka9910.documents.servlets;

import com.github.dimka9910.documents.service.ServiceConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan(basePackages = "com.github.dimka9910.documents.servlets")
@Import({ServiceConfig.class})
public class ServletsConfig {
}
