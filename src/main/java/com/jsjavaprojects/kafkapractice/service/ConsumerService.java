package com.jsjavaprojects.kafkapractice.service;

import com.jsjavaprojects.kafkapractice.dto.OrderHistory;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StoreQueryParameters;
import org.apache.kafka.streams.state.QueryableStoreTypes;
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static com.jsjavaprojects.kafkapractice.utils.CommonStrings.LOG;
import static com.jsjavaprojects.kafkapractice.utils.CommonStrings.ORDER_HISTORY_STORE;
import static com.jsjavaprojects.kafkapractice.utils.LoggingState.ATTEMPT;

@Service
public class ConsumerService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private KafkaStreams kafkaStreams;

    @Autowired
    public ConsumerService(KafkaStreams kafkaStreams){
        this.kafkaStreams=kafkaStreams;
    }

    public OrderHistory findOrderHistory(UUID id){
        logger.info(LOG, ATTEMPT, "findOrderHistory");
        return getStore().get(id.toString());
    }
    public ReadOnlyKeyValueStore<String, OrderHistory> getStore(){
        logger.info(LOG, ATTEMPT, "getStore");
        return kafkaStreams
                .store(
                        StoreQueryParameters.fromNameAndType(
                                ORDER_HISTORY_STORE,
                                QueryableStoreTypes.keyValueStore()
                        )
                );
    }
}
