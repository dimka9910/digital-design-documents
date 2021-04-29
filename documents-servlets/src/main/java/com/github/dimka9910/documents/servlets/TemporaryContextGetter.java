package com.github.dimka9910.documents.servlets;

import com.github.dimka9910.documents.service.ServiceConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

// ВРЕМЕННОЕ РЕШЕНИЕ ТАК КАК ПРЕДЛОЖЕННЫЙ В ЛЕКЦИИ
// applicationContextHolder что то не работает

public class TemporaryContextGetter {
    private static ApplicationContext context;
    private static TemporaryContextGetter tcg;

    public TemporaryContextGetter() {
        context = new AnnotationConfigApplicationContext(ServiceConfig.class);
    }

    public static ApplicationContext getContext() {
        if (tcg == null)
            tcg = new TemporaryContextGetter();

        return context;
    }
}
