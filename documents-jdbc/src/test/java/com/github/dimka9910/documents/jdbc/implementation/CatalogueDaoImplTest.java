package com.github.dimka9910.documents.jdbc.implementation;

import com.github.dimka9910.documents.dto.files.catalogues.CatalogueDto;
import com.github.dimka9910.documents.jdbc.JdbcConfig;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = JdbcConfig.class)
public class CatalogueDaoImplTest {
    @Autowired
    private CatalogueDaoImpl catalogueDaoImpl;

    String rootName = "root";

    @Test
    public void root() {
        CatalogueDto catalogueDto = catalogueDaoImpl.getRootCatalogue();
        Assert.assertNotNull(catalogueDto);
        Assert.assertEquals(catalogueDto.getName(), rootName);
    }

}