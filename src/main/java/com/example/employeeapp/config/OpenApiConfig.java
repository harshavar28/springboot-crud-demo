package com.example.employeeapp.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {

        Server codespace = new Server();
        codespace.setUrl("https://special-memory-qjggprp477vf9pr-8080.app.github.dev");
        codespace.setDescription("GitHub Codespaces");

        Server local = new Server();
        local.setUrl("http://localhost:8080");
        local.setDescription("Local");

        return new OpenAPI()
                .addServersItem(codespace)
                .addServersItem(local);
    }
}
