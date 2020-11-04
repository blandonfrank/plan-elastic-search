package com.personal.plan.elasticsearch.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(info = @Info(
        title = "Personal Capital Plan Search",
        description = "Searching plans on Elastic Search based on different criteria",
        version = "1.0.0",
        contact = @Contact( name = "Frank Blandon")),
        servers = @Server(url = "http://localhost:8080")
)
@SecurityScheme(name = "api", scheme="basic", type = SecuritySchemeType.HTTP, in = SecuritySchemeIn.HEADER)
public class OpenAPIConfiguration {
}
