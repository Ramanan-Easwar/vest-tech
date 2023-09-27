package com.org.investtech.controller;

import com.org.investtech.db.StocksDbDao;
import org.example.model.Asset;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/stocks")
public class StocksController {

    StocksDbDao stocksDbDao;

    @Autowired
    public StocksController(StocksDbDao stocksDbDao) {
        this.stocksDbDao = stocksDbDao;
    }

    @GetMapping
    public ResponseEntity<List<Asset>> getAllListedStocks() {
        return new ResponseEntity<>(stocksDbDao.getListedStocks(), HttpStatus.OK);
    }
}
