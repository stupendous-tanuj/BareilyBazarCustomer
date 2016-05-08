package com.app.bareillybazarcustomer.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.app.bareillybazarcustomer.api.input.Order;
import com.app.bareillybazarcustomer.api.output.Product;
import com.app.bareillybazarcustomer.constant.AppConstant;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by sonia on 18/8/15.
 */
public class PreferenceKeeper {

    private SharedPreferences prefs;
    private static PreferenceKeeper keeper;
    private static Context context;


    public static PreferenceKeeper getInstance() {
        if (keeper == null) {
            keeper = new PreferenceKeeper(context);
        }
        return keeper;
    }

    private PreferenceKeeper(Context context) {
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static void setContext(Context context1) {
        context = context1;
    }


    public void setDeviceInfo(String info) {
        prefs.edit().putString("device_info", info).commit();
    }

    public String getDeviceInfo() {
        return prefs.getString("device_info", "");
    }

    public String getuserMobileNumber() {
        return prefs.getString(AppConstant.PreferenceKeeperNames.USER_MOBILE_NUMBER, "");
    }

    public void setuserMobileNumber(String number) {
        prefs.edit().putString(AppConstant.PreferenceKeeperNames.USER_MOBILE_NUMBER, number).commit();
    }

    public String getUserProfilePicturePath() {
        return prefs.getString(AppConstant.PreferenceKeeperNames.PROFILE_PICTURE_PATH, "");
    }

    public void setUserProfilePicturePath(String picturePath) {
        prefs.edit().putString(AppConstant.PreferenceKeeperNames.PROFILE_PICTURE_PATH, picturePath).commit();
    }

    public void setUserId(String userId) {
        prefs.edit().putString(AppConstant.PreferenceKeeperNames.USER_ID, userId).commit();
    }

    public String getUserId() {
        return prefs.getString(AppConstant.PreferenceKeeperNames.USER_ID, "");
    }

    public String getUserType() {
        return prefs.getString(AppConstant.PreferenceKeeperNames.USER_TYPE, "");
    }

    public void setUserType(String userType) {
        prefs.edit().putString(AppConstant.PreferenceKeeperNames.USER_TYPE, userType).commit();
    }

    public String getLocale() {
        String locale = prefs.getString(AppConstant.PreferenceKeeperNames.LOCALE, "");
        if(locale.equals("") || locale==null)
            return AppConstant.DEFAULT_LOCALE;
        else
            return locale;
    }

    public void setLocale(String locale) {
        prefs.edit().putString(AppConstant.PreferenceKeeperNames.LOCALE, locale).commit();
    }


    public boolean getIsLogin() {
        return prefs.getBoolean(AppConstant.PreferenceKeeperNames.LOGIN, false);
    }

    public void setIsLogin(boolean isLogin) {
        prefs.edit().putBoolean(AppConstant.PreferenceKeeperNames.LOGIN, isLogin).commit();
    }

    public String getOrderId() {
        return prefs.getString(AppConstant.PreferenceKeeperNames.ORDER_ID, "");
    }

    public void setOrderId(String number) {
        prefs.edit().putString(AppConstant.PreferenceKeeperNames.ORDER_ID, number).commit();
    }

    // registration data

    public String getflatNumber() {
        return prefs.getString(AppConstant.PreferenceKeeperNames.FLAT_NUMBER, "");
    }

    public void setflatNumber(String flatNumber) {
        prefs.edit().putString(AppConstant.PreferenceKeeperNames.FLAT_NUMBER, flatNumber).commit();
    }


    public String getArea() {
        return prefs.getString(AppConstant.PreferenceKeeperNames.AREA, "");
    }

    public void setArea(String flatNumber) {
        prefs.edit().putString(AppConstant.PreferenceKeeperNames.AREA, flatNumber).commit();
    }


    public String getLocality() {
        return prefs.getString(AppConstant.PreferenceKeeperNames.LOCALITY, "");
    }

    public void setLocality(String flatNumber) {
        prefs.edit().putString(AppConstant.PreferenceKeeperNames.LOCALITY, flatNumber).commit();
    }

    public String getCity() {
        return prefs.getString(AppConstant.PreferenceKeeperNames.CITY, "");
    }

    public void setCity(String flatNumber) {
        prefs.edit().putString(AppConstant.PreferenceKeeperNames.CITY, flatNumber).commit();
    }

    public String getState() {
        return prefs.getString(AppConstant.PreferenceKeeperNames.STATE, "");
    }

    public void setState(String flatNumber) {
        prefs.edit().putString(AppConstant.PreferenceKeeperNames.STATE, flatNumber).commit();
    }

    public String getPincode() {
        return prefs.getString(AppConstant.PreferenceKeeperNames.PINCODE, "");
    }

    public void setPincode(String flatNumber) {
        prefs.edit().putString(AppConstant.PreferenceKeeperNames.PINCODE, flatNumber).commit();
    }

    public String getLatitute() {
        return prefs.getString(AppConstant.PreferenceKeeperNames.LATITUTE, "");
    }

    public void setLatitute(String flatNumber) {
        prefs.edit().putString(AppConstant.PreferenceKeeperNames.LATITUTE, flatNumber).commit();
    }

    public String getLong() {
        return prefs.getString(AppConstant.PreferenceKeeperNames.LONGITUTE, "");
    }

    public void setLong(String flatNumber) {
        prefs.edit().putString(AppConstant.PreferenceKeeperNames.LONGITUTE, flatNumber).commit();
    }

    public boolean getIsUpdateLocation() {
        return prefs.getBoolean(AppConstant.PreferenceKeeperNames.IS_UPDATE_LOCATION, false);
    }

    public void setIsUpdateLocation(boolean isLogin) {
        prefs.edit().putBoolean(AppConstant.PreferenceKeeperNames.IS_UPDATE_LOCATION, isLogin).commit();
    }


    public String getGCMReg() {
        return prefs.getString(AppConstant.PreferenceKeeperNames.GCM_REG_ID, "");
    }

    public void setGCMReg(String gcmId) {
        prefs.edit().putString(AppConstant.PreferenceKeeperNames.GCM_REG_ID, gcmId).commit();
    }


    public String getName() {
        return prefs.getString(AppConstant.PreferenceKeeperNames.NAME, "");
    }

    public void setName(String gcmId) {
        prefs.edit().putString(AppConstant.PreferenceKeeperNames.NAME, gcmId).commit();
    }


    public String getEmail() {
        return prefs.getString(AppConstant.PreferenceKeeperNames.EMAIL, "");
    }

    public void setEmail(String gcmId) {
        prefs.edit().putString(AppConstant.PreferenceKeeperNames.EMAIL, gcmId).commit();
    }

    public void setProductData(List<Product> contact) {
        if (contact == null) {
            prefs.edit().putString("product_data", null).commit();
        } else {
            prefs.edit().putString("product_data", AppUtil.getJson(contact)).commit();
        }

    }

    public List<Product> getProductData() {
        try {
            List<Product> products;
            Product[] guests = AppUtil.parseJson(prefs.getString("product_data", "{}"), Product[].class);
            products = Arrays.asList(guests);
            List<Product> result = new ArrayList<>();
            result.addAll(products);
            return result;
        } catch (Exception e) {
            return null;
        }
    }

    public void setOrderData(List<Order> contact) {
        if (contact == null) {
            prefs.edit().putString("order_data", null).commit();
        } else {
            prefs.edit().putString("order_data", AppUtil.getJson(contact)).commit();
        }
    }

    public List<Order> getOrderData() {
        try {
            List<Order> favorites;
            Order[] guests = AppUtil.parseJson(prefs.getString("order_data", "{}"), Order[].class);
            favorites = Arrays.asList(guests);
            List<Order> result = new ArrayList<>();
            result.addAll(favorites);
            return result;
        } catch (Exception e) {
            return null;
        }
    }

    public void clearData() {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = pref.edit();
        editor.clear();
        editor.commit();
    }
}
