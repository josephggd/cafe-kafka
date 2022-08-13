package com.jsjavaprojects.kafkapractice.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

import static utils.CommonStrings.customerTopic;
import static utils.CommonStrings.orderTopic;

@Configuration
public class TopicConfiguration {

    @Bean
    public NewTopic buildCustomerTopic() {
        return TopicBuilder.name(customerTopic)
                .build();

    }
    @Bean
    public NewTopic buildOrderTopic() {
        return TopicBuilder.name(orderTopic)
                .build();
    }
}
