package com.jsjavaprojects.kafkapractice.controller;

import com.jsjavaprojects.kafkapractice.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import utils.MenuItem;

import java.util.UUID;

import static utils.CommonStrings.*;
import static utils.LoggingState.ATTEMPT;
import static utils.LoggingState.FAILURE;
import static utils.OrderState.DELIVERED;
import static utils.OrderState.PAID;

@RestController
@RequestMapping("${api.prefix}orders/")
public class OrderProcessingController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final OrderService orderService;

    public OrderProcessingController(
            OrderService orderService
    ){
        this.orderService = orderService;
    }

    @PutMapping("/pay/{id}")
    public ResponseEntity<String> pay(
            @CookieValue(orderIdKey) UUID orderId,
            @CookieValue(menuItemKey) MenuItem menuItem
    ){
        logger.info(LOG, ATTEMPT, PAID);
        try {
            orderService.payForOrder(orderId, menuItem);
            return ResponseEntity.ok(PAID.toString());
        } catch (Exception e) {
            logger.warn(LOG, FAILURE, e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/deliver/{id}")
    public ResponseEntity<String> deliver(
            @CookieValue(orderIdKey) UUID orderId,
            @CookieValue(menuItemKey) MenuItem menuItem
    ){
        logger.info(LOG, ATTEMPT, DELIVERED);
        try {
            orderService.deliverOrder(orderId, menuItem);
            return ResponseEntity.ok(DELIVERED.toString());
        } catch (Exception e) {
            logger.warn(LOG, FAILURE, e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }
}
