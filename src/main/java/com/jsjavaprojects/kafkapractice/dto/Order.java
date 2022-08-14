package com.jsjavaprojects.kafkapractice.dto;

import com.jsjavaprojects.kafkapractice.utils.MenuItem;
import com.jsjavaprojects.kafkapractice.utils.OrderState;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class Order {
    private static final String DATE_FORMAT = "yyyy-MM-dd";

    private UUID orderId;
    private MenuItem menuItem;
    private OrderState currentState;
    private String lastUpdated;
}
