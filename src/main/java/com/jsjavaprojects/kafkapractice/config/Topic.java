package com.jsjavaprojects.kafkapractice.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

import static com.jsjavaprojects.kafkapractice.utils.CommonStrings.ORDER_TOPIC;

@Configuration
public class Topic {
    @Bean
    public NewTopic buildOrderTopic() {
        return TopicBuilder.name(ORDER_TOPIC)
                .build();
    }
}
