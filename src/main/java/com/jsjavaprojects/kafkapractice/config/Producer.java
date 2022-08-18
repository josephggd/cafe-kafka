package com.jsjavaprojects.kafkapractice.config;

import com.jsjavaprojects.kafkapractice.dto.Order;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

import static com.jsjavaprojects.kafkapractice.utils.CommonStrings.LOG;
import static com.jsjavaprojects.kafkapractice.utils.LoggingState.ATTEMPT;

@Configuration
public class Producer {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${spring.kafka.bootstrap-servers}")
    private String servers;

    @Value("${spring.kafka.properties.sasl.jaas.config}")
    private String jaasConfig;

    @Value("${spring.kafka.consumer.group-id}")
    private String groupId;

    @Bean
    @ConditionalOnProperty(name = "cloudkarafka.enabled", havingValue = "true")
    public ProducerFactory<String, Order> cloudProducerFactory(){
        logger.info(LOG, ATTEMPT, "cloudProducerFactory");
        Map<String, Object> props = new HashMap<>();
        props.put("group.id", groupId);
        props.put("sasl.jaas.config", jaasConfig);
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, servers);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return new DefaultKafkaProducerFactory<>(props);
    }

    @Bean
    @ConditionalOnProperty(name = "cloudkarafka.enabled", havingValue = "false")
    public ProducerFactory<String, Order> devProducerFactory(){
        logger.info(LOG, ATTEMPT, "devProducerFactory");
        Map<String, Object> props = new HashMap<>();
        props.put("group.id", groupId);
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, servers);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return new DefaultKafkaProducerFactory<>(props);
    }
    @Bean
    public KafkaTemplate<String, Order> kafkaTemplate(ProducerFactory<String, Order> kafkaProducerFactory) {
        KafkaTemplate<String, Order> kafkaTemplate = new KafkaTemplate<>(kafkaProducerFactory);
        kafkaProducerFactory.createProducer();
        return kafkaTemplate;
    }
}
