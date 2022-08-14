package com.jsjavaprojects.kafkapractice.dto;

import com.jsjavaprojects.kafkapractice.utils.MenuItem;
import com.jsjavaprojects.kafkapractice.utils.OrderState;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import static com.jsjavaprojects.kafkapractice.utils.CommonStrings.DATE_FORMAT;

@Data
@NoArgsConstructor
public class Order {
    private UUID orderId;
    private MenuItem menuItem;
    private OrderState currentState;
    @Setter(AccessLevel.NONE)
    private String lastUpdated;

    public void setLastUpdated(){
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
        this.lastUpdated = LocalDateTime.now().format(dateTimeFormatter);
    }
}
