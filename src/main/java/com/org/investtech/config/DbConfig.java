package com.org.investtech.config;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
@Setter
public class DbConfig {
    @Value("${db.user}")
    String username;
    @Value("${db.password}")
    String password;
    @Value("${db.port}")
    String port;
    @Value("${db.env}")
    String env;

    @Bean
    public Gson getGson(GsonBuilder builder) {
        return builder.setPrettyPrinting().create();
    }
}
