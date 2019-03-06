package com.hamsoft.restapi.controller;

/**
 * Created By kabiruahmed on Mar 2019
 */
@SuppressWarnings("WeakerAccess")
public final class APIName {

    private APIName() {
    }
    // version
    public static final String VERSION = "v1";

    //base URI
    public static final String BASE_URI = "/api/" + VERSION;

    // Users APIs
    public static  final String USERS=  BASE_URI +"/users";

    // auth APIs
    public static final String AUTH_API = BASE_URI + "/auth";
    public static final String USERS_LOGIN = "/login";
    public static final String CREATE_USER = "/register";
    public static final String CHANGE_PASSWORD = "/change-password";
    public static final String RESET_PASSWORD = "/reset-password";
    public static final String RESET_PASSWORD_FINISH = "/reset-password/finish";

    // Categories APIs
    public static  final String CATEGORIES=  BASE_URI +"/categories";

    //Products APIs
    public static  final String PRODUCTS=  BASE_URI +"/products";

    //Companies APIs
    public static  final String COMPANIES=  BASE_URI +"/companies";

}
