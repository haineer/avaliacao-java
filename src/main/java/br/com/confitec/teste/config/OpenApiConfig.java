package br.com.confitec.teste.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class OpenApiConfig {
    
	@Bean
    public OpenAPI customOpenAPI(@Value("${server.app-description}") String appDesciption, @Value("${server.app-version}") String appVersion) {
     return new OpenAPI()
          .info(new Info()
        		  .title("Teste Confitec")
        		  .version(appVersion)
        		  .description(appDesciption)
        		  .license(new License()
        				  .name("MIT")
        				  .url("https://mit-license.org/")
        			)
        	);
    }
}
