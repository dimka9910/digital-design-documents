package com.github.dimka9910.documents.app;

import com.github.dimka9910.documents.rest.config.RestConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({RestConfig.class})
public class ApplicationConfiguration {
}
