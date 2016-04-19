package com.app.customerapp.utils;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.util.Log;

import com.app.customerapp.constant.AppConstant;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.StringTokenizer;
import java.util.concurrent.TimeUnit;


public class AppUtil {


    public static <T> T parseJson(String json, Class<T> tClass) {
        return new Gson().fromJson(json, tClass);
    }

    public static <T> T parseJson(JsonElement result, Class<T> tClass) {
        return new Gson().fromJson(result, tClass);
    }

    public static String getJson(Object profile) {
        return new Gson().toJson(profile);
    }

    public static JsonObject parseJson(String response) {
        JsonObject jo = null;
        JsonElement e = null;
        return new JsonParser().parse(response).getAsJsonObject();
    }

    public static String getDeviceId(Context activity) {
        String android_id = null;
        if (activity != null) {
            android_id = Settings.Secure.getString(activity.getContentResolver(),
                    Settings.Secure.ANDROID_ID);
        }
        return android_id;
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager
                .getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public static boolean checkPlayServices(Context context) {
        int resultCode = GooglePlayServicesUtil
                .isGooglePlayServicesAvailable(context);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode,
                        (Activity) context, AppConstant.PLAY_SERVICES_RESOLUTION_REQUEST)
                        .show();
            } else {
                ((Activity) context).finish();
            }
            return false;
        }
        return true;
    }

    public static double distanceTwoLatLong(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        return (dist);
    }


    public static String getExpectedTimeInHourMinut(long expectedTime) {
        String hms = String.format("%02d:%02d", TimeUnit.MILLISECONDS.toHours(expectedTime), TimeUnit.MILLISECONDS.toMinutes(expectedTime) % TimeUnit.HOURS.toMinutes(1));
        Log.i("EXPECTED TIME : ", hms);
        return hms;
    }

    private static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private static double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }

    public static String setOpenClose(String o, String c) {

        try {
            SimpleDateFormat parser = new SimpleDateFormat("HH");
            Date f;
            f = parser.parse(o);
            int first = Integer.parseInt(parser.format(f));
            f = parser.parse(c);
            int second = Integer.parseInt(parser.format(f));
            Date date = new Date();
            DateFormat dateFormat1 = new SimpleDateFormat("HH");
            int current = Integer.parseInt(dateFormat1.format(date));
            if (first <= current && second >= current) {
                return "Open";
            } else {
                return "Closed";
            }

        } catch (ParseException e) {
            return "Closed";
        }
    }

    public static int getDotData(String o) {
        if (o.contains(".")) {
            StringTokenizer tokenizer = new StringTokenizer(o);
            return Integer.parseInt(tokenizer.nextToken("."));
        } else {
            return Integer.parseInt(o);
        }

    }
}