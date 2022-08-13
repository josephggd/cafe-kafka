package com.jsjavaprojects.kafkapractice.config;

import org.apache.kafka.streams.StreamsConfig;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Streams {

    @Bean
    public StreamsConfig streamsConfig(KafkaProperties kafkaProperties){
        return new StreamsConfig(kafkaProperties.buildStreamsProperties());
    }
}
