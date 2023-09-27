package com.org.investtech.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@Getter
@Setter
public class RabbitMqConfig {

    @Value("${spring.rabbitmq.host}")
    String host;
    @Value("${spring.rabbitmq.username}")
    String username;
    @Value("${spring.rabbitmq.password}")
    String password;
    @Value("${rabbitmq.queue.transaction.buy}")
    String queueBuy;
    @Value("${rabbitmq.queue.transaction.sell}")
    String queueSell;
    @Value("${rabbitmq.queue.transaction.exchange}")
    String exchange;

    private static final String routingKeyPrefix = "_routing_key";

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setHost(this.host);
        connectionFactory.setPort(5672);
        connectionFactory.setUsername(this.username);
        connectionFactory.setPassword(this.password);
        return connectionFactory;
    }

    @Bean
    public AmqpTemplate template(ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(converter());
        return rabbitTemplate;
    }

    @Bean
    public MessageConverter converter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public Queue sellQueue() {
        return new Queue(this.queueSell);
    }

    @Bean
    public Queue buyQueue() {
        return new Queue(this.queueBuy);
    }

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(this.exchange);
    }

    @Bean
    public Binding bindingSell(Queue sellQueue, TopicExchange exchange) {
        return BindingBuilder.bind(sellQueue).to(exchange).with(this.queueSell + routingKeyPrefix);
    }

    @Bean
    public Binding bindingBuy(Queue buyQueue, TopicExchange exchange) {
        return BindingBuilder.bind(buyQueue).to(exchange).with(this.queueBuy + routingKeyPrefix);
    }

    public String getRoutingKeySell() {
        return this.queueSell + routingKeyPrefix;
    }

    public String getRoutingKeyBuy() {
        return this.queueBuy + routingKeyPrefix;
    }

}
