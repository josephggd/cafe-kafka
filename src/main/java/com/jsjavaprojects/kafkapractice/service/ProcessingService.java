package com.jsjavaprojects.kafkapractice.service;

import com.jsjavaprojects.kafkapractice.dto.Order;
import com.jsjavaprojects.kafkapractice.utils.MenuItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static com.jsjavaprojects.kafkapractice.utils.CommonStrings.LOG;
import static com.jsjavaprojects.kafkapractice.utils.CommonStrings.STD_WAIT_TIME;
import static com.jsjavaprojects.kafkapractice.utils.LoggingState.ATTEMPT;
import static com.jsjavaprojects.kafkapractice.utils.OrderState.DELIVERED;
import static com.jsjavaprojects.kafkapractice.utils.OrderState.PAID;

@Service
public class ProcessingService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Value("${spring.kafka.template.default-topic}")
    private String defaultTopic;

    private final KafkaTemplate<String, Order> template;

    public ProcessingService(
            KafkaTemplate<String, Order> template
    ) {
        this.template = template;
    }

    public void payForOrder(
            UUID orderId,
            MenuItem menuItem
    ){
        logger.info(LOG, ATTEMPT, "payOrder");
        Order order = new Order();
        order.setOrderId(orderId);
        order.setCurrentState(PAID);
        order.setMenuItem(menuItem);
        order.setLastUpdated();
        template.send(defaultTopic, order.getOrderId().toString(), order);
    }

    public void deliverOrder(
            UUID orderId,
            MenuItem menuItem
    ) throws InterruptedException {
        logger.info(LOG, ATTEMPT, "deliverOrder");
        Thread.sleep(STD_WAIT_TIME);
        Order order = new Order();
        order.setOrderId(orderId);
        order.setCurrentState(DELIVERED);
        order.setMenuItem(menuItem);
        order.setLastUpdated();
        template.send(defaultTopic, order.getOrderId().toString(), order);
    }
}
