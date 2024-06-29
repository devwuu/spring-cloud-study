package com.example.userservice.config;
import feign.Logger.Level;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LogConfig {

    @Bean
    public Level level(){
        return Level.FULL;
    }


}
