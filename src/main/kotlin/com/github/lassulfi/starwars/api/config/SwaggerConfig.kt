package com.github.lassulfi.starwars.api.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import springfox.documentation.builders.ApiInfoBuilder
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.service.Contact
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2

@Configuration
@EnableSwagger2
class SwaggerConfig {

    private fun getApiMetadata() = ApiInfoBuilder()
            .title("API de Planetas da Saga Star Wars")
            .description("API para cadastro, consulta, atualização e exclusão de planetas da saga Star Wars.")
            .version("1.0.0")
            .contact(Contact("Luis Daniel Assulfi", "https://github.com/lassulfi", "lassulfi@gmail.com"))
            .build()

    @Bean
    fun getSwaggerDocket() = Docket(DocumentationType.SWAGGER_2)
            .select()
            .apis(RequestHandlerSelectors.basePackage("com.github.lassulfi.starwars.api.controller"))
            .paths(PathSelectors.any())
            .build()
            .apiInfo(getApiMetadata())
}