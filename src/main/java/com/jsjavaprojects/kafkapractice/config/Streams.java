package com.jsjavaprojects.kafkapractice.config;

import com.jsjavaprojects.kafkapractice.topology.OrderTopology;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.state.HostInfo;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@Configuration
public class Streams {

    @Value("${spring.kafka.bootstrap-servers}")
    private String servers;

    @Value("${kafka.streams.state.dir:/tmp/kafka-streams/orderHistory}")
    private String stateDir;
    @Value("${kafka.streams.host.info:localhost:8080}")
    private String hostInfo;

    @Bean
    public Properties kafkaStreamsConfiguration(){
        Properties properties = new Properties();
        properties.put(StreamsConfig.APPLICATION_ID_CONFIG, "cafe-kafka");
        properties.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        properties.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        properties.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        properties.put(StreamsConfig.CACHE_MAX_BYTES_BUFFERING_CONFIG, "0");
        properties.put(StreamsConfig.APPLICATION_SERVER_CONFIG, hostInfo);
        properties.put(StreamsConfig.STATE_DIR_CONFIG, stateDir);
        return properties;
    }

    @Bean
    public KafkaStreams kafkaStreams(@Qualifier("kafkaStreamsConfiguration") Properties streamConfiguration) {
        Topology topology = OrderTopology.buildTopology();
        KafkaStreams kafkaStreams = new KafkaStreams(topology, streamConfiguration);
        kafkaStreams.cleanUp();
        kafkaStreams.start();
        Runtime.getRuntime().addShutdownHook(new Thread(kafkaStreams::close));
        return kafkaStreams;
    }

    @Bean
    public HostInfo hostInfo() {
        var split = hostInfo.split(":");
        return new HostInfo(split[0], Integer.parseInt(split[1]));
    }
}
