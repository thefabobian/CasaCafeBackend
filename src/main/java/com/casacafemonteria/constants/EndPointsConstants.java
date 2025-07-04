package com.casacafemonteria.constants;

public class EndPointsConstants {
    private EndPointsConstants() {
        throw new IllegalStateException("Utility class");
    }

    public static final String ENDPOINT_BASE_API = "/api/v1/coffee";
    public static final String ENDPOINT_LOGIN = ENDPOINT_BASE_API + "/login";
    public static final String ENDPOINT_SIGNUP = ENDPOINT_BASE_API + "/signup";
    public static final String ENDPOINT_EMPLOYEE = ENDPOINT_BASE_API + "/employee";
    public static final String ENDPOINT_CUSTOMER = ENDPOINT_BASE_API + "/customer";
    public static final String ENDPOINT_ADMIN = ENDPOINT_BASE_API + "/admin";
    public static final String ENDPOINT_PRODUCT = ENDPOINT_BASE_API + "/product";
    public static final String ENDPOINT_CART = ENDPOINT_BASE_API + "/cart";
    public static final String ENDPOINT_ITEM_CART = ENDPOINT_BASE_API + "/item_cart";
    public static final String ENDPOINT_BILL = ENDPOINT_BASE_API + "/bill";
    public static final String ENDPOINT_DETAIL_BILL = ENDPOINT_BASE_API + "/detail_bill";
}
