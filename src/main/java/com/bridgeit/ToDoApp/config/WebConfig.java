package com.bridgeit.ToDoApp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
@EnableWebMvc
@Configuration
@ComponentScan("com.bridgeit.ToDoApp")
public class WebConfig {
    @Bean
    public RestTemplate template(){
        return new RestTemplate();
    }
	
}
