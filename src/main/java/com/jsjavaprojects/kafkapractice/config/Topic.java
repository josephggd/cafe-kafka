package com.jsjavaprojects.kafkapractice.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

import static com.jsjavaprojects.kafkapractice.utils.CommonStrings.ORDER_HISTORY_TOPIC;

@Configuration
public class Topic {

    @Value("${spring.kafka.template.default-topic}")
    private String defaultTopic;
    @Bean
    public NewTopic buildOrderTopic() {
        return TopicBuilder.name(defaultTopic)
                .build();
    }
    @Bean
    public NewTopic buildOrderHistoryTopic() {
        return TopicBuilder.name(ORDER_HISTORY_TOPIC)
                .build();
    }
}
