package com.jsjavaprojects.kafkapractice.controller;

import com.jsjavaprojects.kafkapractice.service.ProcessingService;
import com.jsjavaprojects.kafkapractice.utils.MenuItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

import static com.jsjavaprojects.kafkapractice.utils.CommonStrings.*;
import static com.jsjavaprojects.kafkapractice.utils.LoggingState.ATTEMPT;
import static com.jsjavaprojects.kafkapractice.utils.LoggingState.FAILURE;
import static com.jsjavaprojects.kafkapractice.utils.OrderState.DELIVERED;
import static com.jsjavaprojects.kafkapractice.utils.OrderState.PAID;

@RestController
@RequestMapping("${api.prefix}/orders/current/")
public class ProcessingController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final ProcessingService processingService;

    public ProcessingController(
            ProcessingService processingService
    ){
        this.processingService = processingService;
    }

    @PutMapping("pay")
    public ResponseEntity<String> pay(
            @CookieValue(ORDER_ID_KEY) UUID orderId,
            @CookieValue(MENU_ITEM_KEY) MenuItem menuItem
    ){
        logger.info(LOG, ATTEMPT, PAID);
        try {
            processingService.payForOrder(orderId, menuItem);
            return ResponseEntity.ok(PAID.toString());
        } catch (Exception e) {
            logger.warn(LOG, FAILURE, e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("deliver")
    public ResponseEntity<String> deliver(
            @CookieValue(ORDER_ID_KEY) UUID orderId,
            @CookieValue(MENU_ITEM_KEY) MenuItem menuItem
    ){
        logger.info(LOG, ATTEMPT, DELIVERED);
        try {
            processingService.deliverOrder(orderId, menuItem);
            return ResponseEntity.ok(DELIVERED.toString());
        } catch (Exception e) {
            logger.warn(LOG, FAILURE, e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }
}
