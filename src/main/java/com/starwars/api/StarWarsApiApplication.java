package com.starwars.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan("com.starwars.api.configuration")
public class StarWarsApiApplication{

	public static void main(String[] args) {
		SpringApplication.run(StarWarsApiApplication.class, args);
	}


}
