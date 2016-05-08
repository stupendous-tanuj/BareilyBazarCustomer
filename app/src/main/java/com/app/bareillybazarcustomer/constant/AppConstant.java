package com.app.bareillybazarcustomer.constant;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sonia on 18/8/15.
 */
public class AppConstant {


    public static final String APPLICATION_ID = "ANDSH1001";
    public static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    public static final String GCM_ID = "645583424970";
    public static boolean PRODUCT_IS_NULL_ORDER_DETAIL = false;
    public static final String DEFAULT_LOCALE = "en";
    public static final String TERMS_CONDITION_LINK = "http://www.google.co.in/";


    public interface FRAGMENT_POS {
        int FRAGMENT_HOME_POSITION = 0;
        int FRAGMENT_MY_EVENT_POSITION = 1;
        int FRAGMENT_SETTING_POSITION = 2;

    }

    public interface BUNDLE_KEY {
        String OTP = "otp";
        String SHOP_ID = "shop_id";
        String SHOP_CATEGORY_NAME = "SHOP_CATEGORY_NAME";
        String MOBILE_EXIT = "mobile_exit";
        String MOBILE_NUMBER = "mobile_number";
        String USER_DETAIL_EXIT = "user_detail_exit";
        String PLACE_ORDER_PRODUCT = "place_order_product";
        String ORDER_ID = "order_id";
        String IS_ADDRESS_UPDATE_ADAPTER = "is_address_update_adapter";

        // address data

        String flatNumberHouseNumber = "flatNumberHouseNumber";
        String addressIdentifier = "addressIdentifier";
        String buildingSocietyStreet = "buildingSocietyStreet";
        String areaLocality = "areaLocality";
        String city = "city";
        String state = "state";
        String country = "country";
        String pincode = "pincode";
        String PRODUCT_CATEGORY_NAME = "productCategoryName";
        String ORDER_DETAIL_DATA = "ORDER_DETAIL_DATA";
        String IS_LOGIN_ACTIVITY = "IS_LOGIN_ACTIVITY";
        String IS_HOME_MY_PROFILE = "IS_HOME_MY_PROFILE";
        String SHOP_DISTANCE = "SHOP_DISTANCE";
        String SHOP_NAME = "shop_name";
        String SHOP_MINIMUM_ORDER = "SHOP_MINIMUM_ORDER";
        String IS_TRACK_RECENT_ACTIVITY = "IS_TRACK_RECENT_ACTIVITY";
        String ORDER_STATUS = "order_status";
        String LOC_UPDTE_LOGIN = "loc_updte";
        String SHOP_CATEGORY_IMGE = "SHOP_CATEGORY_IMGE";
    }

    public interface STATE {
        String STATE_UP = "Uttar Pradesh";
    }

    public static List<String> fetchState() {
        final List<String> stateList = new ArrayList<>();
        stateList.add(STATE.STATE_UP);
        return stateList;
    }

    public interface UserType {
        String SELLER_HUB_TYPE = "SellerHub";
        String SHOP_TYPE = "Shop";
        String GUEST_TYPE = "Guest";
        String DELIVERY_PERSON_TYPE = "DeliveryPerson";
        String ADMIN_TYPE = "Admin";
        String CUSTOMER_TYPE = "Customer";
    }


    public static List<String> fetchCities(String state)
    {
        final List<String> cityList = new ArrayList<>();
        if(state.equals(STATE.STATE_UP)) {
            cityList.add(CITY.CITY_BAREILLY);
        }

        return cityList;
    }

    public interface CITY {
        String CITY_BAREILLY = "Bareilly";
    }

    public interface LANGUAGE {
        String LANGUAGE_ENGLISH = "English";
        String LANGUAGE_MARATHI = "Marathi";
        String LANGUAGE_HINDI = "Hindi";
    }


    public interface PreferenceKeeperNames {
        String USER_MOBILE_NUMBER = "mobile_number";
        String PROFILE_PICTURE_PATH = "PROFILE_PICTURE_PATH";
        String LOGIN = "user_login";
        String LATITUTE = "lat";
        String LONGITUTE = "long";
        String ORDER_ID = "order_id";
        String FLAT_NUMBER = "flat_number";
        String PINCODE = "pincode";
        String STATE = "state";
        String CITY = "city";
        String LOCALITY = "locality";
        String AREA = "area";
        String IS_UPDATE_LOCATION = "IS_UPDATE_LOCATION";
        String EMAIL = "email";
        String NAME = "name";
        String USER_ID = "user_id";
        String USER_TYPE = "user_type";
        String GCM_REG_ID = "gcm_id";
        String LOCALE = "LOCALE";
    }

    public interface RequestCodes {
        int UPDATE_LOCATION = 1;
    }

    public interface DeliveryType {
        String DeliveryType_PickUp = "PickUp";
        String DeliveryType_Delivery = "Delivery";
    }

        public interface STATUS {
        String STATUS_ALL = "ALL";
        String STATUS_APPROVED = "Approved";
        String STATUS_VERIFIED = "Verified";
        String STATUS_REGISTERED = "Registered";
        String STATUS_REJECTED = "Rejected";
        String STATUS_BLOCKED = "Blocked";
        String STATUS_CONFIRMED = "Confirmed";
        String STATUS_ORDERED = "Ordered";
        String STATUS_PREPARED = "Prepared";
        String STATUS_DISPATCHED = "Dispatched";
        String STATUS_DELIVERED = "Delivered";
        String STATUS_CLOSED = "Closed";
        String STATUS_DISPUTED = "Disputed";
        String STATUS_CANCELLED = "Cancelled";
        String STATUS_SUBSCRIBED = "Subscribed";
        String STATUS_INCREASED = "Increased";
        String STATUS_DECREASED = "Decreased";
        String STATUS_UNKNOWN = "Unknown";

    }


    public interface ResponseExtra {
        java.lang.String GET_UPDATE_LOCATION = "GET_UPDATE_LOCATION";
    }

    public interface RESULT_CONSTANT {
        String LOCATION_FRAGMENT = "LOCATION_FRAGMENT";
    }
}
