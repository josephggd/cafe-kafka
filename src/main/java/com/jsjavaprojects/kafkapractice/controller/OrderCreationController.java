package com.jsjavaprojects.kafkapractice.controller;

import com.jsjavaprojects.kafkapractice.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import utils.MenuItem;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

import static utils.CommonStrings.*;
import static utils.LoggingState.ATTEMPT;
import static utils.LoggingState.FAILURE;
import static utils.OrderState.PREPARED;
import static utils.OrderState.RECEIVED;

@RestController
@RequestMapping("${api.prefix}orders/")
public class OrderCreationController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final OrderService orderService;

    public OrderCreationController(
            OrderService orderService
    ){
        this.orderService = orderService;
    }

    @PostMapping("new/{food}")
    public ResponseEntity<String> order(
            @PathVariable("food") MenuItem food,
            HttpServletResponse response
            ){
        logger.info(LOG, ATTEMPT, RECEIVED);
        try {
            UUID idNumber = orderService.createNewOrder(food);
            String status = PREPARED.toString();
            Cookie orderIdCookie = orderService.generateOrderCookie(
                    orderIdKey,
                    idNumber.toString()
                    );
            Cookie menuItemCookie = orderService.generateOrderCookie(
                    menuItemKey,
                    food.toString()
            );
            response.addCookie(orderIdCookie);
            response.addCookie(menuItemCookie);
            return ResponseEntity.ok(status);
        } catch (Exception e) {
            logger.warn(LOG, FAILURE, e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }
}
