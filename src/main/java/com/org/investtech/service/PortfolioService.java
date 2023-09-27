package com.org.investtech.service;

import com.google.gson.Gson;
import com.org.investtech.db.PortfolioDbDAO;
import com.org.investtech.db.StocksDbDao;
import com.org.investtech.helper.RabbitMqHelper;
import com.org.investtech.model.Portfolio;
import com.org.investtech.model.StockOwned;
import org.example.model.RabbitMqStock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class PortfolioService {

    PortfolioDbDAO portfolioDbDAO;
    StocksDbDao stocksDbDao;
    RabbitMqHelper rabbitMqHelper;
    Gson gson;

    Logger logger = LoggerFactory.getLogger(PortfolioService.class);

    @Autowired
    public PortfolioService(PortfolioDbDAO portfolioDbDAO, StocksDbDao stocksDbDao,
                            RabbitMqHelper rabbitMqHelper, Gson gson) {
        this.portfolioDbDAO = portfolioDbDAO;
        this.stocksDbDao = stocksDbDao;
        this.rabbitMqHelper = rabbitMqHelper;
        this.gson = gson;
    }

    public Portfolio getPortfolioForUser(String userAlias) {
        Map<String, Integer> perStockHolding = portfolioDbDAO.getAssetsByUser(userAlias);
        List<StockOwned> stocks = new ArrayList<>(perStockHolding.size());
        // todo: make this entire method in redis toredude load on db
        for(Map.Entry<String, Integer> stock: perStockHolding.entrySet()) {
            AbstractMap.SimpleEntry<String, Float> stockVal = stocksDbDao.getStockPriceByStock(stock.getKey());
                    stocks.add(new StockOwned(stockVal.getKey(),stock.getValue(), stockVal.getValue()));
        }
        return new Portfolio(userAlias, stocks);
    }

    public void submitTransaction(String jsonStockActivity) {
        logger.info("Received purchase order for user:{} ", jsonStockActivity);
        rabbitMqHelper.sendMessage(jsonStockActivity);
    }
}
