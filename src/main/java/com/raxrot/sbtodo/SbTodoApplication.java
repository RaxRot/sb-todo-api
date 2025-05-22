package com.raxrot.sbtodo;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(
        info = @Info(
                title = "TODO API",
                version = "1.0",
                description = "Simple Api for Todo tasks",
                contact = @Contact(
                        name = "RaxRot",
                        email = "dasistperfektos@gmail.com",
                        url = "https://github.com/RaxRot"
                )
        )
)

@SpringBootApplication
public class SbTodoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SbTodoApplication.class, args);
    }

}
