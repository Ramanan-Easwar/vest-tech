package com.org.investtech.controller;

import com.org.investtech.model.Portfolio;
import com.org.investtech.service.PortfolioService;
import org.example.model.Asset;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/portfolio")
public class PortfolioController {
    PortfolioService portfolioService;

    @Autowired
    public PortfolioController(PortfolioService portfolioService) {
        this.portfolioService = portfolioService;
    }

    @GetMapping("/")
    public String healthCheck() {
        return "Ping!! Success!";
    }

    @GetMapping("/user/{user_uuid}")
    public ResponseEntity<Portfolio> getPortfolio(@PathVariable("user_uuid") String userAlias) {
        return new ResponseEntity<>(portfolioService.getPortfolioForUser(userAlias), HttpStatus.OK);
    }


    @PostMapping(value={"/user/{user_uuid}"},
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<String> updatePortfolio(
            @RequestBody String jsonStockActivity) {
        portfolioService.submitTransaction(jsonStockActivity);
        return new ResponseEntity<>("Selling our buying idk", HttpStatus.OK);
    }
}
