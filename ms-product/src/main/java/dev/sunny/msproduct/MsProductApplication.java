package dev.sunny.msproduct;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
        info = @Info(
                title = "Product Microservice API",
                version = "1.0",
                description = "API documentation for the Product Microservice",
                contact = @Contact(
                        name = "Sunny",
                        email = "ssunnyvvats@outlook.com"
                )
        )
)
public class MsProductApplication {

    static void main(String[] args) {
        SpringApplication.run(MsProductApplication.class, args);
    }

}
