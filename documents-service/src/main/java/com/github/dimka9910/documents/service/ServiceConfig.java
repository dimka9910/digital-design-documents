package com.github.dimka9910.documents.service;

import com.github.dimka9910.documents.jpa.JpaConfig;
import com.github.dimka9910.documents.jdbc.JdbcConfig;
import com.github.dimka9910.documents.jdbc.JdbcConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan(basePackages = "com.github.dimka9910.documents.service")
@Import({JpaConfig.class})
//@Import({JdbcConfig.class})
public class ServiceConfig {
}
