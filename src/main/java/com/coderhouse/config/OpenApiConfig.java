package com.coderhouse.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class OpenApiConfig {

	@Bean
	protected OpenAPI customOpenApi() {
		return new OpenAPI().servers(List.of(
				new Server().url("http://localhost:8080").description("Servidor local")
				))
				.info(new Info().title("API REST - Sistema Facturación")
						.version("1.0.0")
						.description("API para gestión de Clientes, Productos y Facturación")
						.contact(new Contact()
								.name("Lucas Prat")
								.email("lucas@email.com")
								)
						
				);
	}
	
	@Bean
    protected RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
