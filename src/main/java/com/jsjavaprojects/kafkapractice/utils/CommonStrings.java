package com.jsjavaprojects.kafkapractice.utils;

public class CommonStrings {
    private CommonStrings(){}

    public static final Integer STD_WAIT_TIME = 5000;
    public static final String DATE_FORMAT = "yyyy-MM-dd hh:mm:ss";
    public static final String LOG = "|| STATE: {} | DESCR: {} ||";
    public static final String ORDER_HISTORY_TOPIC = "orderHistory";

    public static final String ORDER_HISTORY_STORE = "historyStore";

    public static final String COOKIE_PREFIX = "ck-";

    public static final String MENU_ITEM_KEY = COOKIE_PREFIX+"menu-item";

    public static final String ORDER_ID_KEY = COOKIE_PREFIX+"order-id";
    public static final String PATH = "/api/orders/";
}
