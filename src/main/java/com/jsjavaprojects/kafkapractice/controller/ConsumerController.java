package com.jsjavaprojects.kafkapractice.controller;

import com.jsjavaprojects.kafkapractice.dto.OrderHistory;
import com.jsjavaprojects.kafkapractice.service.ConsumerService;
import com.jsjavaprojects.kafkapractice.utils.MenuItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static com.jsjavaprojects.kafkapractice.utils.CommonStrings.*;
import static com.jsjavaprojects.kafkapractice.utils.LoggingState.ATTEMPT;
import static com.jsjavaprojects.kafkapractice.utils.LoggingState.FAILURE;
import static com.jsjavaprojects.kafkapractice.utils.OrderState.DELIVERED;

@RestController
@RequestMapping("${api.prefix}")
public class ConsumerController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final ConsumerService consumerService;

    public ConsumerController(
            ConsumerService consumerService
    ){
        this.consumerService = consumerService;
    }

    @GetMapping("/status")
    public ResponseEntity<OrderHistory> deliver(
            @CookieValue(ORDER_ID_KEY) UUID orderId,
            @CookieValue(MENU_ITEM_KEY) MenuItem menuItem
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
