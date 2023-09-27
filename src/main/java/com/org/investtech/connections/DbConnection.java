package com.org.investtech.connections;

import com.org.investtech.config.DbConfig;
import org.example.exceptions.DbConnectionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;

@Component
public class DbConnection {
    DbConfig dbConfig;

    @Autowired
    public DbConnection(DbConfig dbConfig) {
        this.dbConfig = dbConfig;
    }
    Logger logger = LoggerFactory.getLogger(DbConnection.class);
    public Connection getConnection() {
        Connection connection = null;

        try {
            String url = "jdbc:postgresql://" + dbConfig.getEnv() + ":" + dbConfig.getPort() +
                    "/" + dbConfig.getUsername();
            connection = DriverManager.getConnection(url,
                    dbConfig.getUsername(), dbConfig.getPassword());
            logger.info("we're making a call to db {}", connection.getClientInfo());
            return connection;
        } catch (Exception e) {
            throw new DbConnectionException("cannot connect to DB! trace: " + e);
        }
    }
}
