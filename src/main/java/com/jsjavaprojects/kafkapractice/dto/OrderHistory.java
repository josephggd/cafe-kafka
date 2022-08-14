package com.jsjavaprojects.kafkapractice.dto;

import com.jsjavaprojects.kafkapractice.utils.MenuItem;
import com.jsjavaprojects.kafkapractice.utils.OrderState;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class OrderHistory {
    private static final String DATE_FORMAT = "yyyy-MM-dd";

    private UUID orderId;
    private MenuItem menuItem;
    private OrderState currentState;
    private String receivedDate;
    private String preparedDate;
    private String paidDate;
    private String deliveredDate;

    public OrderHistory process( String uuid, Order order ){
        System.out.println("uuid:"+uuid);
        this.setOrderId(order.getOrderId());
        this.setMenuItem(order.getMenuItem());
        System.out.println(order);
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

