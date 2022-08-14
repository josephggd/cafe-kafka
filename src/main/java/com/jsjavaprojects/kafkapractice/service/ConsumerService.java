package com.jsjavaprojects.kafkapractice.service;

import com.jsjavaprojects.kafkapractice.dto.Order;
import com.jsjavaprojects.kafkapractice.dto.OrderHistory;
import com.jsjavaprojects.kafkapractice.topology.OrderTopology;
import com.jsjavaprojects.kafkapractice.utils.MenuItem;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StoreQueryParameters;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Produced;
import org.apache.kafka.streams.state.QueryableStoreTypes;
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.UUID;

import static com.jsjavaprojects.kafkapractice.utils.CommonStrings.*;
import static com.jsjavaprojects.kafkapractice.utils.LoggingState.ATTEMPT;
import static org.springframework.kafka.annotation.KafkaStreamsDefaultConfiguration.DEFAULT_STREAMS_BUILDER_BEAN_NAME;

@Service
public class ConsumerService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private KafkaStreams kafkaStreams;

    @Autowired
    public ConsumerService(KafkaStreams kafkaStreams){
        this.kafkaStreams=kafkaStreams;
    }

    public OrderHistory findOrderHistory(UUID id){
        return  getStore().get(id.toString());
    }
    public ReadOnlyKeyValueStore<String, OrderHistory> getStore(){
        return kafkaStreams
                .store(
                        StoreQueryParameters.fromNameAndType(
                                ORDER_HISTORY_STORE,
                                QueryableStoreTypes.keyValueStore()
                        )
                );
    }
}
