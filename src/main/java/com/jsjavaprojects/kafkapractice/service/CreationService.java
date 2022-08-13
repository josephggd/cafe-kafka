package com.jsjavaprojects.kafkapractice.service;

import com.jsjavaprojects.kafkapractice.dto.Order;
import com.jsjavaprojects.kafkapractice.utils.MenuItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.UUID;

import static com.jsjavaprojects.kafkapractice.utils.CommonStrings.LOG;
import static com.jsjavaprojects.kafkapractice.utils.CommonStrings.ORDER_TOPIC;
import static com.jsjavaprojects.kafkapractice.utils.LoggingState.ATTEMPT;
import static com.jsjavaprojects.kafkapractice.utils.OrderState.RECEIVED;

@Service
public class CreationService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final KafkaTemplate<String, Order> template;

    public CreationService(
            KafkaTemplate<String, Order> template
    ) {
        this.template = template;
    }

    public UUID createNewOrder(MenuItem menuItem) throws InterruptedException {
        logger.info(LOG, ATTEMPT, "createNewOrder");
        UUID idNumber = UUID.randomUUID();
        Order order = Order
                .builder()
                .orderId(idNumber)
                .currentState(RECEIVED)
                .menuItem(menuItem)
                .lastUpdated(String.valueOf(LocalDate.now()))
                .build();
        template.send(ORDER_TOPIC, order);
        Thread.sleep(5000);
        order.setLastUpdated(String.valueOf(LocalDate.now()));
        template.send(ORDER_TOPIC, order);
        return idNumber;
    }
}
