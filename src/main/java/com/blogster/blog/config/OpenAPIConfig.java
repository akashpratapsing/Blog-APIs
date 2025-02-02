package com.blogster.blog.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class OpenAPIConfig {

  @Value("${my.openapi.dev-url}")
  private String devUrl;

  @Value("${my.openapi.prod-url}")
  private String prodUrl;

  @Bean
  public OpenAPI myOpenAPI() {
    Server devServer = new Server();
    devServer.setUrl(devUrl);
    devServer.setDescription("Server URL in Development environment");

    Server prodServer = new Server();
    prodServer.setUrl(prodUrl);
    prodServer.setDescription("Server URL in Production environment");

    Contact contact = new Contact();
    contact.setEmail("aakashprataps832@gmail.com");
    contact.setName("Akash");
    contact.setUrl("https://www.google.com");

    License mitLicense = new License().name("MIT License").url("https://choosealicense.com/licenses/mit/");

    Info info = new Info()
        .title("Blog App API")
        .version("1.0")
        .contact(contact)
        .description("This API exposes endpoints of Blog App.").termsOfService("Terms of services")
        .license(mitLicense);

    return new OpenAPI()
    		.info(info)
    		.addSecurityItem(new SecurityRequirement().addList("JavaInUseSecurityScheme"))
			.components(new Components().addSecuritySchemes("JavaInUseSecurityScheme", new SecurityScheme()
					.name("JavaInUseSecurityScheme").type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")))
    		.servers(List.of(devServer, prodServer));
  }
}
