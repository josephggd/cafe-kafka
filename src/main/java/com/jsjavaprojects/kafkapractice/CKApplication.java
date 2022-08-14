package com.jsjavaprojects.kafkapractice;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@EnableKafka
@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Order API", version = "1.0", description = "Past/Present Orders"))
public class CKApplication {

    public static void main(String[] args) {
        SpringApplication.run(CKApplication.class, args);
    }

}
