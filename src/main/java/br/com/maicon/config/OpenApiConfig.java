package br.com.maicon.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

/**
 * Classe de configuração para a documentação do OpenAPI (Swagger) na aplicação.
 * <p>
 * Esta classe é responsável por configurar os detalhes da documentação gerada pelo Swagger, 
 * incluindo informações como o título da API, descrição, versão, termos de serviço, contato, 
 * e licenciamento.
 * </p>
 */
@Configuration
public class OpenApiConfig {

    @Bean
    OpenAPI customOpenAPI() {
		return new OpenAPI()
				.info(new Info()
						.title("API Rest Java with Spring Boot")
						.version("v1")
						.description("API Rest by Maicon Moraes")
						.termsOfService("https://github.com/maicon3000")
						.license(new License()
								.name("Apache 2.0")
								.url("https://github.com/maicon3000")
								)
						);
	}

}
