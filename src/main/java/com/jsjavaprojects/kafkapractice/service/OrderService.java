package com.jsjavaprojects.kafkapractice.service;

import com.jsjavaprojects.kafkapractice.dto.OrderDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import utils.MenuItem;

import javax.servlet.http.Cookie;
import java.time.LocalDate;
import java.util.UUID;

import static utils.CommonStrings.*;
import static utils.LoggingState.ATTEMPT;
import static utils.OrderState.*;

@Service
public class OrderService {

    @Autowired
    private ConsumerFactory<String, OrderDto> consumerFactory;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public Cookie generateOrderCookie(
            String key,
            String value
    ){
        String formattedCookieString = String.format("%s-%s",
                cookiePrefix,
                value
                );
        return new Cookie(key, formattedCookieString);
    }


    private final KafkaTemplate<String, OrderDto> template;

    public OrderService (
            KafkaTemplate<String, OrderDto> template
    ) {
        this.template = template;
    }

    public UUID createNewOrder(MenuItem food) throws InterruptedException {
        logger.info(LOG, ATTEMPT, "createNewOrder");
        UUID idNumber = UUID.randomUUID();
        OrderDto orderDto = OrderDto
                .builder()
                .number(idNumber)
                .currentState(RECEIVED)
                .menuItem(food)
                .lastUpdated(String.valueOf(LocalDate.now()))
                .build();
        template.send(orderTopic, orderDto );
        Thread.sleep(5000);
        orderDto.setLastUpdated(String.valueOf(LocalDate.now()));
        template.send(orderTopic, orderDto );
        return idNumber;
    }

    public void payForOrder(
            MenuItem menuItem,
            UUID orderId
    ){
        logger.info(LOG, ATTEMPT, "payForOrder");
        OrderDto orderDto = OrderDto.builder()
                .number(orderId)
                .currentState(PAID)
                .menuItem(menuItem)
                .lastUpdated(String.valueOf(LocalDate.now()))
                .build();
        template.send(orderTopic, orderDto );
    }

    public void deliverOrder(
            MenuItem menuItem,
            UUID orderId
    ) throws InterruptedException {
        logger.info(LOG, ATTEMPT, "deliverOrder");
        Thread.sleep(5000);
        OrderDto orderDto = OrderDto.builder()
                .number(orderId)
                .currentState(DELIVERED)
                .menuItem(menuItem)
                .lastUpdated(String.valueOf(LocalDate.now()))
                .build();
        template.send(orderTopic, orderDto );
    }
}
