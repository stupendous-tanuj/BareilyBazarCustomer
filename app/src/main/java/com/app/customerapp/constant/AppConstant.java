package com.app.customerapp.constant;

/**
 * Created by sonia on 18/8/15.
 */
public class AppConstant {


    public static final String APPLICATION_ID = "ANDSH1001";
    public static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    public static final String GCM_ID = "389845974615";
    public static boolean PRODUCT_IS_NULL_ORDER_DETAIL = false;

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

    public interface PreferenceKeeperNames {
        String USER_MOBILE_NUMBER = "mobile_number";
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
        String GCM_REG_ID = "gcm_id";
        String EMAIL = "email";
        String NAME = "name";
    }

    public interface RequestCodes {
        int UPDATE_LOCATION = 1;
    }

    public interface STATUS {
        String STATUS_UNKNOWN = "Unknown";
    }


    public interface ResponseExtra {
        java.lang.String GET_UPDATE_LOCATION = "GET_UPDATE_LOCATION";
    }

    public interface RESULT_CONSTANT {
        String LOCATION_FRAGMENT = "LOCATION_FRAGMENT";
    }
}
