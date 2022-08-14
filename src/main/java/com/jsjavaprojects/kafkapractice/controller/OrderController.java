package com.jsjavaprojects.kafkapractice.controller;

import com.jsjavaprojects.kafkapractice.dto.OrderHistory;
import com.jsjavaprojects.kafkapractice.service.ConsumerService;
import com.jsjavaprojects.kafkapractice.service.CreationService;
import com.jsjavaprojects.kafkapractice.service.ProcessingService;
import com.jsjavaprojects.kafkapractice.utils.MenuItem;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

import static com.jsjavaprojects.kafkapractice.utils.CommonStrings.*;
import static com.jsjavaprojects.kafkapractice.utils.LoggingState.ATTEMPT;
import static com.jsjavaprojects.kafkapractice.utils.LoggingState.FAILURE;
import static com.jsjavaprojects.kafkapractice.utils.OrderState.*;

@RestController
@RequestMapping("${api.prefix}/orders/")
@Tag(name = "Order API", description = "API for making and aggregating Orders")
public class OrderController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final CreationService newOrderSvc;
    private final ProcessingService processingService;
    private final ConsumerService consumerService;

    public OrderController(
            CreationService newOrderSvc,
            ProcessingService processingService,
            ConsumerService consumerService
    ){
        this.newOrderSvc = newOrderSvc;
        this.processingService = processingService;
        this.consumerService = consumerService;
    }
    public Cookie generateOrderCookie(
            String key,
            String value,
            String path
    ){
        Cookie newCookie = new Cookie(key, value);
        newCookie.setMaxAge(60*60);
        newCookie.setPath(path);
        return newCookie;
    }


    @PostMapping("{menuItem}")
//    @Operation(description = "Initiate 'food-ordering' process by 'calling in' an order", parameters = {
//            @Parameter(name = "name", in = ParameterIn.QUERY, required = true, description = "name parameter")
//    })
    public ResponseEntity<String> order(
            @PathVariable("menuItem") MenuItem menuItem,
            HttpServletResponse response
            ){
        logger.info(LOG, ATTEMPT, RECEIVED);
        try {
            UUID idNumber = newOrderSvc.createNewOrder(menuItem);
            String status = PREPARED.toString();
            Cookie orderIdCookie = generateOrderCookie(
                    ORDER_ID_KEY,
                    idNumber.toString(),
                    PATH
                    );
            Cookie menuItemCookie = generateOrderCookie(
                    MENU_ITEM_KEY,
                    menuItem.toString(),
                    PATH
            );
            response.addCookie(orderIdCookie);
            response.addCookie(menuItemCookie);
            return ResponseEntity.ok(status);
        } catch (InterruptedException e) {
            logger.warn(LOG, FAILURE, e.getMessage());
            Thread.currentThread().interrupt();
        } catch (Exception e) {
            logger.warn(LOG, FAILURE, e.getMessage());
        }
        return ResponseEntity.badRequest().build();
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
        } catch (InterruptedException e) {
            logger.warn(LOG, FAILURE, e.getMessage());
            Thread.currentThread().interrupt();
        } catch (Exception e) {
            logger.warn(LOG, FAILURE, e.getMessage());
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("byId")
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
