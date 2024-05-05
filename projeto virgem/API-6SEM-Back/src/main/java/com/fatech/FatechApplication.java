package com.fatech;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "API - Fatech", version = "1", description = "API desenvolvida para documentação das rotas do backend"))
public class FatechApplication {

	public static void main(String[] args) {
		SpringApplication.run(FatechApplication.class, args);
	}

}
