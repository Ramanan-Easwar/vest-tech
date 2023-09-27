package com.org.investtech.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StockOwned {
    String stockName;
    Integer stockUnits;
    Float perStockValue;
    Float totalValue;

    public StockOwned(String stockName, Integer stockUnits, Float perStockValue) {
        this.stockName = stockName;
        this.stockUnits = stockUnits;
        this.perStockValue = perStockValue;
        this.totalValue = stockUnits * perStockValue;
    }

    @Override
    public String toString() {
        return "StockOwned{" +
                "stockName='" + stockName + '\'' +
                ", stockUnits=" + stockUnits +
                ", perStockValue=" + perStockValue +
                '}';
    }
}
