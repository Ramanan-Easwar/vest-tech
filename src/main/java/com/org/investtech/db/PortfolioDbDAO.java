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
