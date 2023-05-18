package com.chatapp.backend.configuration;

import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.security.*;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;


@Configuration
@SecurityScheme(
  name = "Bearer Authentication",
  type = SecuritySchemeType.HTTP,
  bearerFormat = "JWT",
  scheme = "bearer"
)
public class OpenApiConfig {}