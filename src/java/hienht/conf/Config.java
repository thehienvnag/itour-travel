package hienht.conf;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author thehien
 */
public class Config {
   
    public static final int PAGE_SIZE = 20;
    
    //SITE URL CONFIG
    public static final String PAGE_LOGIN = "login-page"; 
    public static final String PAGE_TRY = "try"; 
    public static final String PAGE_HOME = "home"; 
    public static final String PAGE_INVALID = "invalid";
    public static final String PAGE_CART = "view-cart";
    public static final String PAGE_FINISH_BOOKING = "finish-booking";
    public static final String PAGE_BOOKING_HISTORY = "view-booking-history";
    public static final String PAGE_MANAGE_BOOKING = "manage-booking";
    
    //Url for FilterDispatcher
    public static final String URL_AUTH = "auth";
    
    //JSP
    public static final String JSP_HOME = "home.jsp";
    public static final String JSP_LOGIN = "login.jsp";
    public static final String JSP_CART = "view-cart.jsp";
    public static final String JSP_CREATE_TOUR = "create-tour.jsp";
    public static final String JSP_AFTER_BOOKING = "after-booking.jsp";
    public static final String JSP_BOOKING_HISTORY = "booking-history.jsp";
    public static final String JSP_MANAGE_BOOKING = "manage-booking.jsp";
    
    //CONTROLLER
    public static final String CONTROLLER_PAY_MOMO = "PayWithMomoServlet";
    
    
    //ROLE CONFIG
    public static final int ROLE_ADMIN = 1;
    public static final int ROLE_USER = 2;
    
    //STATUS CONFIG
    public static final int STAT_ACTIVE = 1;
    public static final int STAT_DEACTIVE = 2;
    
    //SESSION ATTRIBUTES
    //Session
    public static final String ATTR_USER = "USER";
    //Request
    public static final String ATTR_DISCOUNTS = "LIST_DISCOUNTS";
    public static final String ATTR_TOURS = "LIST_TOUR";
    public static final String ATTR_SEARCH_LINK = "SEARCH_LINK";
    public static final String ATTR_TOTAL_PAGES = "TOTAL_PAGES";
    public static final String ATTR_LAST_SEARCH_URL = "LAST_SEARCH_URL";
    public static final String ATTR_PAGE_SIZE = "PAGE_SIZE";
    public static final String ATTR_TOTAL_ROWS = "TOTAL_ROWS";
    public static final String ATTR_BOOKING = "BOOKING_CONFIRM";
    public static final String ATTR_BOOKING_LIST = "BOOKING_LIST";
    public static final String ATTR_BOOKING_STATUS = "BOOKING_STATUS";
    public static final String ATTR_ERR_PAYMENT_TYPE = "ERR_PAYMENT_TYPE";
    public static final String ATTR_IMG_LINK = "IMG_LINK";
    public static final String ATTR_LOCATION = "LOCATION";
    public static final String ATTR_TOUR_NAME = "TOUR_NAME";
    public static final String ATTR_QUOTA = "QUOTA";
    public static final String ATTR_PRICE = "PRICE";
    public static final String ATTR_DATE_START = "DATE_START";
    public static final String ATTR_DATE_END = "DATE_END";
    public static final String ATTR_BOOKING_ID = "BOOKING_ID";
    public static final String ATTR_PAYMENT_TYPE = "PAYMENT_TYPE";
    public static final String ATTR_ERR_LOGIN = "ERR_LOGIN";
    public static final String ATTR_ERR_SUCCEED_BOOKING = "ERR_SUCCEEDED";
    public static final String ATTR_INVALID_TOUR_DATE = "INVALID_TOUR_DATE";
    public static final String ATTR_INVALID_QUOTA = "INVALID_QUOTA";
    public static final String ATTR_INVALID_DISCOUNT = "INVALID_DISCOUNT";
    
   
    //Front end variable
    public static final double MAX_PRICE_SEARCH = 30000000;
    public static final String DEFAULT_PRICE_SEARCH = "0,30000000";
}
