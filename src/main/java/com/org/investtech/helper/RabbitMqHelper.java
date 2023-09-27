package com.org.investtech.helper;

import com.google.gson.Gson;
import com.org.investtech.config.RabbitMqConfig;
import org.example.model.RabbitMqStock;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RabbitMqHelper {

    RabbitTemplate rabbitTemplate;
    RabbitMqConfig rabbitMqConfig;
    Gson gson;

    @Autowired
    public RabbitMqHelper(RabbitTemplate rabbitTemplate,
                          RabbitMqConfig rabbitMqConfig, Gson gson) {
        this.rabbitTemplate = rabbitTemplate;
        this.rabbitMqConfig = rabbitMqConfig;
        this.gson = gson;
    }

    public void sendMessage(String json) {
        RabbitMqStock rabbitMqStock = gson.fromJson(json, RabbitMqStock.class);
        if("BUY".equals(rabbitMqStock.getPurchaseType())) {
            rabbitTemplate.convertAndSend(rabbitMqConfig.getExchange(),
                    rabbitMqConfig.getRoutingKeyBuy(), rabbitMqStock);
        }
        else {
            rabbitTemplate.convertAndSend(rabbitMqConfig.getExchange(),
                    rabbitMqConfig.getRoutingKeySell(), rabbitMqStock);
        }
    }

}
