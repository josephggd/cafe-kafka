package com.jsjavaprojects.kafkapractice.dto;

import com.jsjavaprojects.kafkapractice.utils.MenuItem;
import com.jsjavaprojects.kafkapractice.utils.OrderState;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class OrderHistory {
    private UUID orderId;
    private MenuItem menuItem;
    private OrderState currentState;
    private String receivedDate;
    private String preparedDate;
    private String paidDate;
    private String deliveredDate;

    public OrderHistory process( Order order ){
        this.setOrderId(order.getOrderId());
        this.setMenuItem(order.getMenuItem());
        this.setCurrentState(order.getCurrentState());
        switch (order.getCurrentState()){
            case RECEIVED:
                this.setReceivedDate(order.getLastUpdated());
                break;
            case PREPARED:
                this.setPreparedDate(order.getLastUpdated());
                break;
            case PAID:
                this.setPaidDate(order.getLastUpdated());
                break;
            case DELIVERED:
                this.setDeliveredDate(order.getLastUpdated());
                break;
        }
        return this;
    }
}

