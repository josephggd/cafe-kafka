package com.jsjavaprojects.kafkapractice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.EnableKafkaStreams;

@SpringBootApplication
public class CKApplication {

    public static void main(String[] args) {
        SpringApplication.run(CKApplication.class, args);
    }

}
