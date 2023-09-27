package com.org.investtech.db;

import com.org.investtech.connections.DbConnection;
import org.example.exceptions.SqlException;
import org.example.model.Asset;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class PortfolioDbDAO {

    Logger logger = LoggerFactory.getLogger(PortfolioDbDAO.class);
    DbConnection dbConnection;

    @Autowired
    public PortfolioDbDAO(DbConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    public Map<String, Integer> getAssetsByUser(String userAlias) {
        Map<String, Integer> assetHolding = new HashMap<>();
        String GET_HOLDING_BY_USER = "SELECT stock_alias, holding from stock_count where user_alias = ?;";
        ResultSet rs;
        try(Connection conn = dbConnection.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(GET_HOLDING_BY_USER)) {
            preparedStatement.setString(1, userAlias);
            rs = preparedStatement.executeQuery();
            while(rs.next()) {
                assetHolding.put(rs.getString(1), rs.getInt(2));
            }
            return assetHolding;
        } catch (Exception e) {
            throw new SqlException("Error while getting stock holdings:  " + e);
        }
    }
}
/*
* create stock : insert into stock values(13833536, 'Alienware','d66a193c-d721-4d5d-b11d-1e55aeee76fb', 900, 900, 25);
* -- purchase--
*
* 1. create a transaction:
* insert into transaction values(74008304, '9cd46b53-aac4-4676-a0d2-01758469bd79',
* 'e303db29-2f79-4f99-8f4a-67cb85b79ce9', 'd66a193c-d721-4d5d-b11d-1e55aeee76fb', 25, 'BUY');
*
* 2. complete purchase
* insert into stock_count values(36334500, 'e303db29-2f79-4f99-8f4a-67cb85b79ce9', 'd66a193c-d721-4d5d-b11d-1e55aeee76fb', 1);
* */