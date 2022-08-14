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
import static com.jsjavaprojects.kafkapractice.utils.OrderState.DELIVERED;
import static com.jsjavaprojects.kafkapractice.utils.OrderState.PAID;

@Service
public class ProcessingService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

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
        order.setLastUpdated(String.valueOf(LocalDate.now()));
        template.send(ORDER_TOPIC, order.getOrderId().toString(), order);
    }

    public void deliverOrder(
            UUID orderId,
            MenuItem menuItem
    ) throws InterruptedException {
        logger.info(LOG, ATTEMPT, "deliverOrder");
        Thread.sleep(5000);
        Order order = new Order();
        order.setOrderId(orderId);
        order.setCurrentState(DELIVERED);
        order.setMenuItem(menuItem);
        order.setLastUpdated(String.valueOf(LocalDate.now()));
        template.send(ORDER_TOPIC, order.getOrderId().toString(), order);
    }
}
