package com.fiap.postech.postech.challenge.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition
@Configuration
public class openApiConfig {
    @Bean
    public OpenAPI restaurante_challenge(){
        return new OpenAPI()
                .info(new Info().title("Restaurante Challenge")
                                .description("Projeto desenvolvido para o challenge fase 01")
                                .version("v0.0.1"));


    }
}
