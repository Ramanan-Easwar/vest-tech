package com.org.investtech.db;

import com.org.investtech.connections.DbConnection;
import org.example.exceptions.SqlException;
import org.example.model.Asset;
import org.example.model.Stock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;


@Component
public class StocksDbDao {
    DbConnection dbConnection;

    @Autowired
    public StocksDbDao(DbConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    public AbstractMap.SimpleEntry<String, Float> getStockPriceByStock(String stockAlias) {
        String GET_STOCK_VALUE = "SELECT stock_name, stock_price from stock where stock_alias = ?;";
        ResultSet rs;
        try(Connection conn = dbConnection.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(GET_STOCK_VALUE)) {
            preparedStatement.setString(1, stockAlias);
            rs = preparedStatement.executeQuery();
            if(rs.next()) {
                return new AbstractMap.SimpleEntry<>(rs.getString(1), rs.getFloat(2));
            }
            return null;
        } catch (Exception e) {
            throw new SqlException("Error while getting stock holdings:  " + e);
        }
    }

    public List<Asset> getListedStocks() {

        List<Asset> stocks = new ArrayList<>();
        String GET_STOCK_VALUE = "SELECT stock_id, stock_name, stock_alias, stock_issued, " +
                "stock_available, stock_price from stock;";
        ResultSet rs;
        try(Connection conn = dbConnection.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(GET_STOCK_VALUE)) {
            rs = preparedStatement.executeQuery();
            while(rs.next()) {
                stocks.add(new Stock(rs.getLong(1), rs.getString(2),
                        rs.getString(3), rs.getInt(4), rs.getInt(5),
                        rs.getFloat(6)));
            }
            return stocks;
        } catch (Exception e) {
            throw new SqlException("Error while getting stock holdings:  " + e);
        }
    }
}
