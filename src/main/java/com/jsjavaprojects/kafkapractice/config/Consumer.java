package com.jsjavaprojects.kafkapractice.config;

import com.jsjavaprojects.kafkapractice.dto.Order;
import com.jsjavaprojects.kafkapractice.serialization.OrderDeserializer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;

@Configuration
public class Consumer {

    @Value("${spring.kafka.bootstrap-servers}")
    private String servers;

    public Properties consumerConfig(){
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, servers);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, UUID.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, OrderDeserializer.class);
        return props;
    }
    @Bean
    public ConsumerFactory<UUID, Order> consumerFactory(){
        return new DefaultKafkaConsumerFactory<>(consumerConfig());
    }

    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<UUID, Order>> listenerFactory(){
        ConcurrentKafkaListenerContainerFactory<UUID, Order> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }
}