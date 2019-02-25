package com.origamisoftware.teach.advanced.util;

import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.Statement;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 *  Tests for the DatabaseUtils class
 */
public class DatabaseUtilsTest {
	
    @Test
    public void testGetConnection() throws Exception{
        Connection connection = DatabaseUtils.getConnection();
        assertNotNull("verify that we can get a connection ok",connection);
    }

    @Test
    public void testGetConnectionWorks() throws Exception{
        Connection connection = DatabaseUtils.getConnection();
        Statement statement = connection.createStatement();
        boolean execute = statement.execute("select * from quotes");
        assertTrue("verify that we can execute a statement",execute);
    }
	
	@Test(expected = DatabaseInitializationException.class)
	public void testInitializeDatabase() throws Exception{
	
		DatabaseUtils.initializeDatabase("C:/Users/lmrjr/Adv_Java_Class_Week5/src/main/sql/stocks_db_initialization");
	}		
}
