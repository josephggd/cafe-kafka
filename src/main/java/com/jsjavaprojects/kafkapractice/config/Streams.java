package com.jsjavaprojects.kafkapractice.config;

import com.jsjavaprojects.kafkapractice.serialization.OrderDeserializer;
import com.jsjavaprojects.kafkapractice.topology.OrderTopology;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.Topology;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Configuration
public class Streams {

    @Value("${spring.kafka.bootstrap-servers}")
    private String servers;

    @Value("${kafka.streams.state.dir:/tmp/kafka-streams/orders}")
    private String stateDir;
    @Value("${kafka.streams.host.info:localhost:8080}")
    private String hostInfo;

    @Bean
    public Properties getProperties(){
        Properties properties = new Properties();
        properties.put(StreamsConfig.APPLICATION_ID_CONFIG, "order-app");
        properties.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, servers);
        properties.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String());
        properties.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String());
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        properties.put(StreamsConfig.CACHE_MAX_BYTES_BUFFERING_CONFIG, "0");
        properties.put(StreamsConfig.APPLICATION_SERVER_CONFIG, hostInfo);
        properties.put(StreamsConfig.STATE_DIR_CONFIG, stateDir);
        return properties;
    }

    @Bean
    public KafkaStreams kafkaStreams( ){
        System.out.println("1111111111111111");
        Topology topology = OrderTopology.buildTopology();
        System.out.println("2222222222");
        KafkaStreams kafkaStreams = new KafkaStreams(topology, getProperties());
        System.out.println("333333333333");
        kafkaStreams.cleanUp();
        System.out.println("4444444444");
        kafkaStreams.start();
        System.out.println("55555555555");
        Runtime.getRuntime().addShutdownHook(new Thread(kafkaStreams::close));
        System.out.println("66666666666666");
        return kafkaStreams;
    }
}
