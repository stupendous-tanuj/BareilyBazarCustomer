package com.app.bareillybazarcustomer.network;

import android.content.Context;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.app.bareillybazarcustomer.api.Error;
import com.app.bareillybazarcustomer.utils.AppUtil;
import com.app.bareillybazarcustomer.utils.Logger;

import org.json.JSONObject;

public abstract class AppHttpJsonListner<T> implements Response.Listener<JSONObject>, Response.ErrorListener {
    private static final String TAG = AppHttpJsonListner.class.getCanonicalName();
    private Context ctx;

    public AppHttpJsonListner(Context ctx) {
        this.ctx = ctx;
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        if (error != null && error.networkResponse != null && error.networkResponse.statusCode == 403) {
            onError(new Error(""));

        }
    }

    @Override
    public void onResponse(JSONObject obj) {
        Logger.INFO(TAG, "onResponse : " + obj.toString());
        try {
            if ("success".equals(obj.get("status")) || obj.getInt("status") == 1) {
                onSuccess(obj.getJSONObject("response"));
            } else {
                if (obj.getJSONObject("response").has("errors")) {
                    _onError(AppUtil.parseJson(obj.getJSONObject("response").toString(), Error.class));
                } else {
                    onError(new Error(""));
                }
            }
        } catch (Exception e) {
            onError(new Error(""));
        }
    }

    public abstract void onSuccess(JSONObject jsonObject);

    public abstract void onError(Error error);

    private void _onError(Error errors) {

    }


}
