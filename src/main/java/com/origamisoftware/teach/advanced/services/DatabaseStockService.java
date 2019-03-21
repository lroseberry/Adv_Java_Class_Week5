package com.origamisoftware.teach.advanced.services;

import com.origamisoftware.teach.advanced.model.StockQuote;
import com.origamisoftware.teach.advanced.util.DatabaseConnectionException;
import com.origamisoftware.teach.advanced.util.DatabaseUtils;
import com.origamisoftware.teach.advanced.util.IntervalEnums;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.*;

/**
 * An implementation of the StockService interface that gets
 * stock data from a database.
 */
public class DatabaseStockService implements StockService {
    BigDecimal price = new BigDecimal(163.50);
    BigDecimal addValue = new BigDecimal("1");
    /**
     * Return the current price for a share of stock  for the given symbol
     *
     * @param symbol the stock symbol of the company you want a quote for.
     *               e.g. APPL for APPLE
     * @return a  <CODE>BigDecimal</CODE> instance
     * @throws StockServiceException if using the service generates an exception.
     *                               If this happens, trying the service may work, depending on the actual cause of the
     *                               error.
     */
    @Override
	@NotNull
    public StockQuote getQuote(String symbol) throws StockServiceException {
        // todo - this is a pretty lame implementation why?
        // Since this method only returns a single StockQuote it should probably be the most recent one.
        // Return the most recent stock price for the symbol
        List<StockQuote> stockQuotes = null;
        try {
            Connection connection = DatabaseUtils.getConnection();
            Statement statement = connection.createStatement();
            String queryString = "select * from (select * from quotes order by time DESC) X where symbol = '" + symbol + "'";
            ResultSet resultSet = statement.executeQuery(queryString);
            stockQuotes = new ArrayList<>(resultSet.getFetchSize());
            while(resultSet.next()) {
                String symbolValue = resultSet.getString("symbol");
                java.util.Date time = resultSet.getDate("time");
                BigDecimal price = resultSet.getBigDecimal("price");
                stockQuotes.add(new StockQuote(price, time, symbolValue));
            }

        } catch (DatabaseConnectionException | SQLException exception) {
            throw new StockServiceException(exception.getMessage(), exception);
        }
        if (stockQuotes.isEmpty()) {
            throw new StockServiceException("There is no stock data for:" + symbol);
        }
        return stockQuotes.get(0);
    }

    /**
     * Get a historical list of stock quotes for the provide symbol
     *
     * @param symbol the stock symbol to search for
     * @param from   the date of the first stock quote
     * @param until  the date of the last stock quote
     * @return a list of StockQuote instances
     * @throws   StockServiceException if using the service generates an exception.
     * If this happens, trying the service may work, depending on the actual cause of the
     * error.
     */
    @Override
	@NotNull
    public List<StockQuote> getQuote(String symbol, Calendar from, Calendar until) throws StockServiceException {
        List<StockQuote> stockQuotes = null;
            try {
                Connection connection = DatabaseUtils.getConnection();
                Statement statement = connection.createStatement();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy MMM dd HH:mm:ss:SS");
                String stringFromDate = (simpleDateFormat.format(from.getTime()));
                String stringUntilDate = (simpleDateFormat.format(until.getTime()));
                String queryString = "select * from quotes where symbol = '" + symbol + "' and time >= '" + stringFromDate + "' and time < '" + stringUntilDate + "'";
                ResultSet resultSet = statement.executeQuery(queryString);
                stockQuotes = new ArrayList<>(resultSet.getFetchSize());
                while(resultSet.next()) {
                    String symbolValue = resultSet.getString("symbol");
                    java.util.Date time = resultSet.getDate("time");
                    BigDecimal price = resultSet.getBigDecimal("price");
                    stockQuotes.add(new StockQuote(price, time, symbolValue));
                }

            } catch (DatabaseConnectionException | SQLException exception) {
                throw new StockServiceException(exception.getMessage(), exception);
        }
        if (stockQuotes.isEmpty()) {
            throw new StockServiceException("There is no stock data for: " + symbol);
        }
        return stockQuotes;
}
/**
     * Gets the {@code List} of {@code StockQuote} instances for the given symbol, date range, and interval
     * @param symbol    symbol for the company issuing the stock
     * @param startDate beginning of the date range
     * @param endDate   end of the date range
	 * @param interval  time elapsed between stockQuote instances
     * @return a {@code List} of {@code StockQuote} instances
     */
    @Override
	@NotNull
    public List<StockQuote> getQuote(String symbol, Calendar startDate, Calendar endDate, IntervalEnums interval) {

        BigDecimal price = new BigDecimal(163.50);
        List<StockQuote> stockQuotesList = new ArrayList<StockQuote>();
        endDate.add(Calendar.DATE, 1);

        while (startDate.before(endDate)) {
            java.util.Date date = startDate.getTime();
            stockQuotesList.add(new StockQuote(price, date, symbol));
            price = price.add(new BigDecimal(1));
            startDate.add(Calendar.HOUR_OF_DAY, interval.getNumVal());
        }
        return stockQuotesList;
    }
}
