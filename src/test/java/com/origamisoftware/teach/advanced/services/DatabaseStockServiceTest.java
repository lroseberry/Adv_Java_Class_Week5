package com.origamisoftware.teach.advanced.services;

import com.origamisoftware.teach.advanced.model.StockQuote;
import com.origamisoftware.teach.advanced.util.IntervalEnums;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import java.util.Calendar;
import java.util.List;

/**
 * Unit tests for the DatabaseStockService
 */
public class DatabaseStockServiceTest {

    @Test
    public void testGetQuote() throws Exception {
        DatabaseStockService databaseStockService = new DatabaseStockService();
        String symbol = "APPL";
        StockQuote stockQuote = databaseStockService.getQuote(symbol);
        assertNotNull("Verify we can get a stock quote from the db", stockQuote);
        assertEquals("Make sure the symbols match", symbol, stockQuote.getSymbol());
    }
	
	@Test
    public void testGetQuoteThreeArgs() throws Exception {
        DatabaseStockService databaseStockService = new DatabaseStockService();
        String symbol = "GOOG";
		Calendar from =  Calendar.getInstance();
		from.set(Calendar.MONTH, Calendar.FEBRUARY);
		from.set(Calendar.DAY_OF_MONTH, 1);
		from.set(Calendar.YEAR,2018);
		Calendar until =  Calendar.getInstance();
		until.set(Calendar.MONTH, Calendar.FEBRUARY);
		until.set(Calendar.DAY_OF_MONTH, 1);
		until.set(Calendar.YEAR,2020);		
		List<StockQuote> stockQuotes = databaseStockService.getQuote(symbol, from, until);
        assertNotNull("Verify we can get a stock quote from the db", stockQuotes);
        assertEquals("Make sure the symbols match", symbol, stockQuotes.get(0).getSymbol());
    }
	
	@Test
    public void testGetQuoteFourArgs() throws Exception {
        DatabaseStockService databaseStockService = new DatabaseStockService();
        String symbol = "GOOG";
		Calendar from =  Calendar.getInstance();
		from.set(Calendar.MONTH, Calendar.FEBRUARY);
		from.set(Calendar.DAY_OF_MONTH, 1);
		from.set(Calendar.YEAR,2018);
		Calendar until =  Calendar.getInstance();
		until.set(Calendar.MONTH, Calendar.FEBRUARY);
		until.set(Calendar.DAY_OF_MONTH, 1);
		until.set(Calendar.YEAR,2020);	
		IntervalEnums interval = IntervalEnums.HOURLY;
		List<StockQuote> stockQuotes = databaseStockService.getQuote(symbol, from, until, interval);
        assertNotNull("Verify we can get a stock quote from the db", stockQuotes);
        assertEquals("Make sure the symbols match", symbol, stockQuotes.get(0).getSymbol());
    }
}
