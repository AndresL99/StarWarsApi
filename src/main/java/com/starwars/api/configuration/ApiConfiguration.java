package com.starwars.api.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.net.http.HttpClient;

@Configuration
public class ApiConfiguration {

    @Bean
    public HttpClient httpClient() {
        return HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .build();
    }

    @Bean
    public WebMvcConfigurer corsConfig()
        {
        return new WebMvcConfigurer(){
            public void addCorsMapping(CorsRegistry corsRegistry)
            {
                corsRegistry.addMapping("/**")
                        .allowedMethods("GET", "POST");
            }
        };
    }
}
