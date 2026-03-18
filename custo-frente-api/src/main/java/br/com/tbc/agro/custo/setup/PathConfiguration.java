package br.com.tbc.agro.custo.setup;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.HandlerTypePredicate;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Slf4j
@Configuration
public class PathConfiguration implements WebMvcConfigurer {

    @Override
    public void configurePathMatch(final PathMatchConfigurer configurer) {
        log.info("Init configure path api...");

        configurer.addPathPrefix("api",
                HandlerTypePredicate.forBasePackage("br.com.tbc.agro.custo.controller"));
    }
}
