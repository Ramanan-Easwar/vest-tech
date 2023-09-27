package com.org.investtech.model;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Portfolio {

    String username;
    List<StockOwned> stockOwned;

    public Portfolio(String username, List<StockOwned> stockOwned) {
        this.username = username;
        this.stockOwned = stockOwned;
    }

    @Override
    public String toString() {
        return "Portfolio{" +
                "username='" + username + '\'' +
                ", stockOwned=" + stockOwned +
                '}';
    }
}
