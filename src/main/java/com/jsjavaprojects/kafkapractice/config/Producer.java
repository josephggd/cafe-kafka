package com.jsjavaprojects.kafkapractice.config;

import com.jsjavaprojects.kafkapractice.dto.Order;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
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

    public Map<String, Object> orderConfig(){
        logger.info(LOG, ATTEMPT, "orderConfig");
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, servers);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
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
