package org.sopt.server.utils;

/**
 * Created by ds on 2018-10-23.
 */
public class ResponseMessage {

    public static final String LOGIN_SUCCESS = "Login Success";
    public static final String LOGIN_FAIL = "Login Fail";

    public static final String READ_USER = "Success Find User";
    public static final String CREATED_USER = "Success Save User";
    public static final String NOT_FOUND_USER = "Not Find User";
    public static final String ALREADY_USER = "Already User";

    public static final String READ_PRODUCT = "Success Find Product";
    public static final String READ_PRODUCT_LIST = "Success Find Product List";
    public static final String NOT_FOUND_PRODUCT = "Not Find Product";

    public static final String READ_PAYMENT = "Success Find Payment";
    public static final String READ_PAYMENT_LIST = "Success Find Payment List";
    public static final String READ_PAYMENT_FAIL = "Fail Read Payment";
    public static final String PAYMENT_SUCCESS = "Success Payment";
    public static final String PAYMENT_FAIL = "Fail Payment";
    public static final String NOT_FOUND_PAYMENT = "Not Find Payment";

    public static final String UNAUTHORIZED = "UnAuthorization";
    public static final String INTERNAL_SERVER_ERROR = "Fail";
}
