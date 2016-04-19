package com.app.customerapp.network;

import com.app.customerapp.activity.HomeActivity;
import com.app.customerapp.api.input.OrderRequest;
import com.app.customerapp.api.output.AddressIdentifierResponse;
import com.app.customerapp.api.output.CommonResponse;
import com.app.customerapp.api.output.DeliveryAddressResponse;
import com.app.customerapp.api.output.ExpectedDeliveryTimeResponse;
import com.app.customerapp.api.output.LoginResponse;
import com.app.customerapp.api.output.MyOrderDetailResponse;
import com.app.customerapp.api.output.PlaceOrderResponse;
import com.app.customerapp.api.output.ProductCategoryResponse;
import com.app.customerapp.api.output.ProductCatelogResponse;
import com.app.customerapp.api.output.ProfileResponse;
import com.app.customerapp.api.output.RecentOrderResponse;
import com.app.customerapp.api.output.ShopCategoryResponse;
import com.app.customerapp.api.output.ShopDetailResponse;
import com.app.customerapp.constant.AppConstant;
import com.app.customerapp.utils.Logger;
import com.app.customerapp.utils.PreferenceKeeper;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

/*
 * AppRequestBuilder : All api access code
 */
public class AppRequestBuilder {

    public static String TAG = HomeActivity.class.getSimpleName();
    private static final String BASE_URL = "http://stupendoustanuj.co.nf/ShopTheFortune";

    // http://stupendoustanuj.co.nf/ShopTheFortune/ Verify_Mobile_Number.php

    private static void setUserHeader(Map<String, String> map) {
        map.put("applicationId", AppConstant.APPLICATION_ID);
        map.put("userId", PreferenceKeeper.getInstance().getuserMobileNumber());
    }

    public static AppHttpRequest loginAPI(String mobileNumber, AppResponseListener<LoginResponse> appResponseListener) {
        AppHttpRequest request = AppHttpRequest.getPostRequest(BASE_URL + "/Verify_Mobile_Number.php", appResponseListener);
        Map<String, String> map = new LinkedHashMap<String, String>();
        map.put("applicationId", AppConstant.APPLICATION_ID);
        map.put("userId", mobileNumber);
        map.put("customerMobileNumber", mobileNumber);
        request.addParam("input", setRequestBody(map));
        return request;
    }

    // http://stupendoustanuj.co.nf/ShopTheFortune/ Register_User.php

    public static AppHttpRequest registerUserDetailAPI(String mobileNumber,
                                                       String customerName,
                                                       String customerEmail,
                                                       String customerFlatNumberSociety,
                                                       String customerArea,
                                                       String customerPincode,
                                                       String customerCity,
                                                       String customerState,
                                                       String customerLatitude,
                                                       String customerLongitude,
                                                       String gcmId,
                                                       AppResponseListener<LoginResponse> appResponseListener) {
        AppHttpRequest request = AppHttpRequest.getPostRequest(BASE_URL + "/Register_User.php", appResponseListener);
        Map<String, String> map = new LinkedHashMap<String, String>();
        setUserHeader(map);
        map.put("OTPVerified", "1");
        map.put("deviceID", PreferenceKeeper.getInstance().getDeviceInfo());
        map.put("customerMobileNumber", mobileNumber);
        map.put("customerName", customerName);
        map.put("customerEmail", customerEmail);
        map.put("customerFlatNumberSociety", customerFlatNumberSociety);
        map.put("customerArea", customerArea);
        map.put("customerPincode", customerPincode);
        map.put("customerCity", customerCity);
        map.put("customerState", customerState);
        map.put("customerCountry", "India");
        map.put("customerLatitude", customerLatitude);
        map.put("customerLongitude", customerLongitude);
        map.put("customerGCMID", gcmId);

        request.addParam("input", setRequestBody(map));

        return request;
    }

    public static AppHttpRequest getShopCategoryAPI(AppResponseListener<ShopCategoryResponse> appResponseListener) {

        AppHttpRequest request = AppHttpRequest.getPostRequest(BASE_URL + "/Fetch_Shop_Categories.php", appResponseListener);
        Map<String, String> map = new LinkedHashMap<String, String>();
        setUserHeader(map);
        request.addParam("input", setRequestBody(map));
        return request;
    }

    // http://stupendoustanuj.co.nf/ShopTheFortune/ Fetch_Shops.php
    /// http://stupendoustanuj.co.nf/ShopTheFortune/Fetch_Shops.php?input={%22shopCategory%22:%22Name1%22,%22applicationId%22:%22SH1001%22,%22userId%22:%22SH1001%22}

    public static AppHttpRequest fetchShopCategoryDetailAPI(String shopName, AppResponseListener<ShopDetailResponse> appResponseListener) {
        AppHttpRequest request = AppHttpRequest.getPostRequest(BASE_URL + "/Fetch_Shops.php", appResponseListener);
        Map<String, String> map = new LinkedHashMap<String, String>();
        setUserHeader(map);
        map.put("shopCategory", shopName);
        request.addParam("input", setRequestBody(map));
        return request;
    }

    private static String setRequestBody(Map<String, String> map) {
        Gson gson = new Gson();
        String json = gson.toJson(map, LinkedHashMap.class);
        Logger.INFO("REQUEST", "JSON : " + json);
        return json;
    }

    // http://stupendoustanuj.co.nf/ShopTheFortune/Fetch_Shop_Product_Categories.php
    // Fetch_Shop_Product_Categories

    public static AppHttpRequest fetchProductCategoriesAPI(String shopId, AppResponseListener<ProductCategoryResponse> appResponseListener) {
        AppHttpRequest request = AppHttpRequest.getPostRequest(BASE_URL + "/Fetch_Shop_Product_Categories.php", appResponseListener);
        Map<String, String> map = new LinkedHashMap<String, String>();
        setUserHeader(map);
        map.put("shopId", shopId);

        request.addParam("input", setRequestBody(map));
        return request;
    }

    // http://stupendoustanuj.co.nf/ShopTheFortune/Fetch_Products.php
//  // shopid , shopCategoryName , productCategoryName

    public static AppHttpRequest fetchProductCatelogAPI(String shopId, String shopCategoryName, String productCategoryName, AppResponseListener<ProductCatelogResponse> appResponseListener) {
        AppHttpRequest request = AppHttpRequest.getPostRequest(BASE_URL + "/Fetch_Products.php", appResponseListener);
        Map<String, String> map = new LinkedHashMap<String, String>();
        setUserHeader(map);
        map.put("shopId", shopId);
        map.put("shopCategoryName", shopCategoryName);
        map.put("productCategoryName", productCategoryName);
        request.addParam("input", setRequestBody(map));
        return request;
    }
    // http://stupendoustanuj.co.nf/ShopTheFortune/Fetch_Products.php

    public static AppHttpRequest fetchProductCategoriesDetailAPI(String pcategoryName, String sCategoryName, String shopId, AppResponseListener<ProductCatelogResponse> appResponseListener) {
        AppHttpRequest request = AppHttpRequest.getPostRequest(BASE_URL + "/Fetch_Products.php", appResponseListener);
        Map<String, String> map = new LinkedHashMap<String, String>();
        setUserHeader(map);
        map.put("productCategoryName", pcategoryName);
        map.put("shopCategoryName", sCategoryName);
        map.put("shopId", shopId);

        request.addParam("input", setRequestBody(map));
        return request;
    }

    //  http://stupendoustanuj.co.nf/ShopTheFortune/Place_A_Order.php

    public static AppHttpRequest placeOrderAPI(OrderRequest orderRequest, AppResponseListener<PlaceOrderResponse> appResponseListener) {
        AppHttpRequest request = AppHttpRequest.getPostRequest(BASE_URL + "/Place_A_Order.php", appResponseListener);
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(new Gson().toJson(orderRequest));

            Logger.INFO(TAG, "PLACE : " + jsonObject.toString());
            request.addParam("input", jsonObject.toString());
        } catch (JSONException e) {
        }
        return request;
    }
// http://stupendoustanuj.co.nf/ShopTheFortune/Add_A_Address.php

    public static AppHttpRequest addAddressAPI(String flatHouseNumber, String addressIdentifier, String buildingSStreet, String areaLocality,
                                               String city, String state, String pincode, AppResponseListener<CommonResponse> appResponseListener) {
        AppHttpRequest request = AppHttpRequest.getPostRequest(BASE_URL + "/Add_A_Address.php", appResponseListener);

        Map<String, String> map = new LinkedHashMap<String, String>();
        setUserHeader(map);
        map.put("flatNumberHouseNumber", flatHouseNumber);
        map.put("addressIdentifier", addressIdentifier);
        map.put("buildingSocietyStreet", buildingSStreet);
        map.put("areaLocality", areaLocality);
        map.put("city", city);
        map.put("state", state);
        map.put("pincode", pincode);
        request.addParam("input", setRequestBody(map));
        return request;
    }

    // http://stupendoustanuj.co.nf/ShopTheFortune/Update_A_Address.php
    public static AppHttpRequest updateAddressAPI(String flatHouseNumber, String addressId, String buildingSStreet, String areaLocality,
                                                  String city, String state, String pincode, AppResponseListener<CommonResponse> appResponseListener) {
        AppHttpRequest request = AppHttpRequest.getPostRequest(BASE_URL + "/Update_A_Address.php", appResponseListener);

        Map<String, String> map = new LinkedHashMap<String, String>();
        setUserHeader(map);
        map.put("flatNumberHouseNumber", flatHouseNumber);
        map.put("addressIdentifier", addressId);
        map.put("buildingSocietyStreet", buildingSStreet);
        map.put("areaLocality", areaLocality);
        map.put("city", city);
        map.put("state", state);
        map.put("pincode", pincode);
        request.addParam("input", setRequestBody(map));
        return request;
    }

    /// http://stupendoustanuj.co.nf/ShopTheFortune/Remove_A_Address.php
    public static AppHttpRequest removeAddressAPI(String addressId, AppResponseListener<CommonResponse> appResponseListener) {
        AppHttpRequest request = AppHttpRequest.getPostRequest(BASE_URL + "/Remove_A_Address.php", appResponseListener);

        Map<String, String> map = new LinkedHashMap<String, String>();
        setUserHeader(map);
        map.put("addressIdentifier", addressId);
        request.addParam("input", setRequestBody(map));
        return request;
    }
// http://stupendoustanuj.co.nf/ShopTheFortune/Fetch_Addresses.php


    public static AppHttpRequest fetchAddressAPI(AppResponseListener<DeliveryAddressResponse> appResponseListener) {
        AppHttpRequest request = AppHttpRequest.getPostRequest(BASE_URL + "/Fetch_Addresses.php", appResponseListener);

        Map<String, String> map = new LinkedHashMap<String, String>();
        setUserHeader(map);
        request.addParam("input", setRequestBody(map));
        return request;
    }


    ////  new code here
    // Fetch_Customer_Profile http://stupendoustanuj.co.nf/ShopTheFortune/Fetch_Customer_Profile.php

    public static AppHttpRequest fetchProfileData(AppResponseListener<ProfileResponse> appResponseListener) {
        AppHttpRequest request = AppHttpRequest.getPostRequest(BASE_URL + "/Fetch_Customer_Profile.php", appResponseListener);

        Map<String, String> map = new LinkedHashMap<String, String>();
        setUserHeader(map);
        request.addParam("input", setRequestBody(map));
        return request;
    }

    // http://stupendoustanuj.co.nf/ShopTheFortune/Update_Customer_Profile.php
    public static AppHttpRequest updateProfileData(String customerName,
                                                   String customerEmail,
                                                   String customerFlatNumberSociety,
                                                   String customerArea,
                                                   String customerPincode,
                                                   String customerCity,
                                                   String customerState,
                                                   String customerLongitude,
                                                   String customerLatitude,
                                                   AppResponseListener<LoginResponse> appResponseListener) {
        AppHttpRequest request = AppHttpRequest.getPostRequest(BASE_URL + "/Update_Customer_Profile.php", appResponseListener);
        Map<String, String> map = new LinkedHashMap<String, String>();
        setUserHeader(map);
        map.put("customerName", customerName);
        map.put("customerEmail", customerEmail);
        map.put("customerFlatNumberSociety", customerFlatNumberSociety);
        map.put("customerArea", customerArea);
        map.put("customerPincode", customerPincode);
        map.put("customerCity", customerCity);
        map.put("customerState", customerState);
        map.put("customerLongitude", customerLongitude);
        map.put("customerLatitude", customerLatitude);
        request.addParam("input", setRequestBody(map));
        return request;
    }

    // http://stupendoustanuj.co.nf/ShopTheFortune/Fetch_All_Orders.php

    public static AppHttpRequest fetchMyOrderDetailAPI(String userType, AppResponseListener<MyOrderDetailResponse> appResponseListener) {
        AppHttpRequest request = AppHttpRequest.getPostRequest(BASE_URL + "/Fetch_All_Orders.php", appResponseListener);

        Map<String, String> map = new LinkedHashMap<String, String>();
        setUserHeader(map);
        map.put("userType", userType);
        request.addParam("input", setRequestBody(map));
        return request;
    }

    // http://stupendoustanuj.co.nf/ShopTheFortune/ Fetch_Order_Details.php

    public static AppHttpRequest fetchRecentTracOrderAPI(String orderId, String orderStatus, AppResponseListener<RecentOrderResponse> appResponseListener) {
        AppHttpRequest request = AppHttpRequest.getPostRequest(BASE_URL + "/Fetch_Order_Details.php", appResponseListener);

        Map<String, String> map = new LinkedHashMap<String, String>();
        setUserHeader(map);
        map.put("orderId", orderId);
        map.put("orderStatus", orderStatus);
        request.addParam("input", setRequestBody(map));
        return request;
    }

    //  http://stupendoustanuj.co.nf/ShopTheFortune/Fetch_Expected_DeliveryTime.php
    public static AppHttpRequest fetchDeliveryTimeAPI(AppResponseListener<ExpectedDeliveryTimeResponse> appResponseListener) {
        AppHttpRequest request = AppHttpRequest.getPostRequest(BASE_URL + "/Fetch_Expected_DeliveryTime.php", appResponseListener);

        Map<String, String> map = new LinkedHashMap<String, String>();
        setUserHeader(map);
        request.addParam("input", setRequestBody(map));
        return request;
    }

    //  http://stupendoustanuj.co.nf/ShopTheFortune/Fetch_Addresses.php
    public static AppHttpRequest fetchDeliveryAddressAPI(AppResponseListener<AddressIdentifierResponse> appResponseListener) {
        AppHttpRequest request = AppHttpRequest.getPostRequest(BASE_URL + "/Fetch_Addresses.php", appResponseListener);

        Map<String, String> map = new LinkedHashMap<String, String>();
        setUserHeader(map);
        request.addParam("input", setRequestBody(map));
        return request;
    }

    // http://stupendoustanuj.co.nf/ShopTheFortune/Contact_us.php
    public static AppHttpRequest contactUsSendMessageApi(String message, AppResponseListener<CommonResponse> appResponseListener) {
        AppHttpRequest request = AppHttpRequest.getPostRequest(BASE_URL + "/Contact_us.php", appResponseListener);

        Map<String, String> map = new LinkedHashMap<String, String>();
        setUserHeader(map);
        map.put("message", message);
        request.addParam("input", setRequestBody(map));
        return request;
    }

    public static AppHttpRequest getLocationsBasedOnString(String loc, LocationResponseListener<ArrayList> appResponseListener) {
        return AppHttpRequest.getGetRequest("https://maps.googleapis.com/maps/api/place/autocomplete/" + loc, appResponseListener);
    }

    // http://stupendoustanuj.co.nf/ShopTheFortune/Update_GCMID.php
    public static AppHttpRequest updateGCMID(String gcmID, AppResponseListener<CommonResponse> appResponseListener) {
        AppHttpRequest request = AppHttpRequest.getPostRequest(BASE_URL + "/Update_GCMID.php", appResponseListener);
        Map<String, String> map = new LinkedHashMap<String, String>();
        setUserHeader(map);
        map.put("userType", "Customer");
        map.put("GCMID", gcmID);
        request.addParam("input", setRequestBody(map));
        return request;
    }

    ///
    ///  http://stupendoustanuj.co.nf/ShopTheFortune/Verify_ApplicationID.php

    public static AppHttpRequest verifyApp(AppResponseListener<CommonResponse> appResponseListener) {
        AppHttpRequest request = AppHttpRequest.getPostRequest(BASE_URL + "/Verify_ApplicationID.php", appResponseListener);
        Map<String, String> map = new LinkedHashMap<String, String>();
        setUserHeader(map);
        request.addParam("input", setRequestBody(map));
        return request;
    }

}
