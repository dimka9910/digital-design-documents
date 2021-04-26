package com.github.dimka9910.documents.jdbc.implementation;

import com.github.dimka9910.documents.jdbc.DbConnection;
import junit.framework.TestCase;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

public class CatalogueDaoImplTest extends TestCase {
    Connection cn = DbConnection.getConnection("");

    @Test
    public void simpleTst(){
        System.out.println("hellp");
    }
  
}