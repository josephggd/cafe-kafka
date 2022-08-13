package com.jsjavaprojects.kafkapractice.serialization;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jsjavaprojects.kafkapractice.dto.Order;
import com.jsjavaprojects.kafkapractice.utils.LoggingState;
import lombok.NoArgsConstructor;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.util.Map;

import static com.jsjavaprojects.kafkapractice.utils.CommonStrings.LOG;

@NoArgsConstructor
public class OrderDeserializer implements Deserializer<Order> {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        // Neither are used
    }

    @Override
    public Order deserialize(String s, byte[] bytes) {
        logger.info(LOG, LoggingState.ATTEMPT, "deserialize");
        try {
            if (bytes == null) {
                return null;
            } else {
                String byteString = new String(bytes, StandardCharsets.UTF_8);
                return objectMapper.readValue(byteString, Order.class);
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
