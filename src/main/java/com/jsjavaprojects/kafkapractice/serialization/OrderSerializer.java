package com.jsjavaprojects.kafkapractice.serialization;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jsjavaprojects.kafkapractice.dto.Order;
import com.jsjavaprojects.kafkapractice.utils.LoggingState;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Serializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

import static com.jsjavaprojects.kafkapractice.utils.CommonStrings.LOG;

public class OrderSerializer implements Serializer<Order> {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    private final ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        // Neither are used
    }

    @Override
    public byte[] serialize(String s, Order order) {
        logger.info(LOG, LoggingState.ATTEMPT, "deserialize");
        try {
            if (order == null) {
                return new byte[0];
            } else {
                return objectMapper.writeValueAsBytes(order);
            }
        } catch (Exception e) {
            logger.warn(LOG, LoggingState.FAILURE, e.getMessage());
            throw new SerializationException("ERROR");
        }
    }

    @Override
    public void close() {
        // Neither are used
    }
}
