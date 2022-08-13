package com.jsjavaprojects.kafkapractice.utils;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class CommonStrings {
    public static final String LOG = "|| STATE: {} | DESCR: {} ||";
    public static final String ORDER_TOPIC = "order";

    public static final String COOKIE_PREFIX = "ck-";

    public static final String MENU_ITEM_KEY = COOKIE_PREFIX+"menu-item";

    public static final String ORDER_ID_KEY = COOKIE_PREFIX+"order-id";
    public static final String payPath = "/api/orders/current/";
}
