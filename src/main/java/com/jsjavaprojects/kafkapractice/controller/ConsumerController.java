package com.jsjavaprojects.kafkapractice.controller;

import com.jsjavaprojects.kafkapractice.dto.OrderHistory;
import com.jsjavaprojects.kafkapractice.service.ConsumerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

import static com.jsjavaprojects.kafkapractice.utils.CommonStrings.LOG;
import static com.jsjavaprojects.kafkapractice.utils.CommonStrings.ORDER_ID_KEY;
import static com.jsjavaprojects.kafkapractice.utils.LoggingState.ATTEMPT;
import static com.jsjavaprojects.kafkapractice.utils.LoggingState.FAILURE;
import static com.jsjavaprojects.kafkapractice.utils.OrderState.DELIVERED;

@RestController
@RequestMapping("${api.prefix}/history/")
public class ConsumerController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final ConsumerService consumerService;

    public ConsumerController(
            ConsumerService consumerService
    ){
        this.consumerService = consumerService;
    }

    @GetMapping("/byId")
    public ResponseEntity<OrderHistory> deliver(
            @CookieValue(ORDER_ID_KEY) UUID orderId
    ){
        logger.info(LOG, ATTEMPT, DELIVERED);
        try {
            OrderHistory orderHistory = consumerService.findOrderHistory(orderId);
            return ResponseEntity.ok(orderHistory);
        } catch (Exception e) {
            logger.warn(LOG, FAILURE, e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }
}
