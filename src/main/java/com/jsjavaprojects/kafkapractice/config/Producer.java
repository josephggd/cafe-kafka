package com.jsjavaprojects.kafkapractice.config;

import com.jsjavaprojects.kafkapractice.dto.Order;
import com.jsjavaprojects.kafkapractice.serialization.OrderSerializer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Configuration
public class Producer {

    @Value("${spring.kafka.bootstrap-servers}")
    private String servers;

    public Map<String, Object> orderConfig(){
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, servers);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, UUID.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, OrderSerializer.class);
        return props;
    }
    @Bean
    public ProducerFactory<String, Order> orderProducerFactory(){
        return new DefaultKafkaProducerFactory<>(orderConfig());
    }

    @Bean
    public KafkaTemplate<String, Order> orderTemplate(
            ProducerFactory<String, Order> factory
    ){
        return new KafkaTemplate<>(factory);
    }
}
