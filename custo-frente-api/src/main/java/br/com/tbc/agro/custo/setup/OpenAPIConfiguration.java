package br.com.tbc.agro.custo.setup;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.info.BuildProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class OpenAPIConfiguration {

    private final BuildProperties properties;

    @Bean
    public OpenAPI myOpenAPI() {

        final var contact = new Contact();
        contact.setName("TBC Agro");

        final var licence = new License().name("TBC License");


        final var info = new Info()
                .title(properties.get("title"))
                .version(properties.getVersion())
                .contact(contact)
                .description(properties.get("description"))
                .license(licence);

        return new OpenAPI().info(info);
    }

}
