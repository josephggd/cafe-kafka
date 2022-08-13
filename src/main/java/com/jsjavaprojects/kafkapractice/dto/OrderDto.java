package com.jsjavaprojects.kafkapractice.dto;

import lombok.*;
import org.springframework.lang.Nullable;
import utils.MenuItem;
import utils.OrderState;

import java.util.UUID;

@Data
@RequiredArgsConstructor
@Builder
public class OrderDto {
    private static final String DATE_FORMAT = "yyyy-MM-dd";

    @Nullable
    @Setter(AccessLevel.NONE)
    private UUID number;
    private MenuItem menuItem;
    private OrderState currentState;
    @Nullable
    private String lastUpdated;
}
