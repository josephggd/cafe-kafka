package com.jsjavaprojects.kafkapractice.controller;

import com.jsjavaprojects.kafkapractice.service.CreationService;
import com.jsjavaprojects.kafkapractice.utils.MenuItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

import static com.jsjavaprojects.kafkapractice.utils.CommonStrings.*;
import static com.jsjavaprojects.kafkapractice.utils.LoggingState.ATTEMPT;
import static com.jsjavaprojects.kafkapractice.utils.LoggingState.FAILURE;
import static com.jsjavaprojects.kafkapractice.utils.OrderState.PREPARED;
import static com.jsjavaprojects.kafkapractice.utils.OrderState.RECEIVED;

@RestController
@RequestMapping("${api.prefix}/orders/new/")
public class CreationController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final CreationService newOrderSvc;

    public CreationController(
            CreationService newOrderSvc
    ){
        this.newOrderSvc = newOrderSvc;
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
                    payPath
                    );
            Cookie menuItemCookie = generateOrderCookie(
                    MENU_ITEM_KEY,
                    menuItem.toString(),
                    payPath
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
