package com.github.dimka9910.documents.service.service;

import com.github.dimka9910.documents.dto.files.catalogues.CatalogueDto;
import com.github.dimka9910.documents.jdbc.JdbcConfig;
import com.github.dimka9910.documents.jdbc.implementation.CatalogueDaoImpl;
import com.github.dimka9910.documents.service.ServiceConfig;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ServiceConfig.class)
public class CatalogueServiceTest {
    @Autowired
    private CatalogueDaoImpl catalogueDaoImpl;

    String rootName = "root";

    @Test
    public void name() {
        Assert.assertTrue(catalogueDaoImpl != null);
    }
}