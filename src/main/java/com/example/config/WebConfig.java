package com.example.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Permitir CORS para todas las rutas
                .allowedOrigins("*") // Permitir todos los orígenes, puedes restringir a orígenes específicos
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Métodos permitidos
                .allowedHeaders("*") // Permitir todos los encabezados
                .allowCredentials(false) // Si necesitas enviar cookies, ponlo en true
                .maxAge(3600); // Tiempo máximo en cache para las opciones pre-flight
    }
}