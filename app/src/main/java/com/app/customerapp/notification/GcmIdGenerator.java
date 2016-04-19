package com.app.customerapp.notification;

import android.os.AsyncTask;
import android.util.Log;

import com.app.customerapp.activity.CustomerAppBaseActivity;
import com.app.customerapp.activity.HomeActivity;
import com.app.customerapp.api.output.CommonResponse;
import com.app.customerapp.api.output.ErrorObject;
import com.app.customerapp.constant.AppConstant;
import com.app.customerapp.network.AppHttpRequest;
import com.app.customerapp.network.AppRequestBuilder;
import com.app.customerapp.network.AppResponseListener;
import com.app.customerapp.network.AppRestClient;
import com.app.customerapp.utils.PreferenceKeeper;
import com.google.android.gms.gcm.GoogleCloudMessaging;

import java.io.IOException;


public class GcmIdGenerator {

    private String TAG = HomeActivity.class.getSimpleName();
    private final CustomerAppBaseActivity context;
    private final PreferenceKeeper prefs;
    private GoogleCloudMessaging gcm;

    public GcmIdGenerator(CustomerAppBaseActivity context) {
        this.context = context;
        prefs = PreferenceKeeper.getInstance();
    }

    /**
     * generates a gcm registration id
     */
    public void getGCMRegId() {
        new GCMIdGeneratorTask().execute();
    }

    private class GCMIdGeneratorTask extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {
            String regid = "";
            try {
                if (gcm == null) {
                    gcm = GoogleCloudMessaging.getInstance(context);
                }
                regid = gcm.register(AppConstant.GCM_ID);

            } catch (IOException ex) {

            }
            Log.i(TAG, "REG ID : " + regid);

            return regid;
        }

        @Override
        protected void onPostExecute(String regid) {
            super.onPostExecute(regid);
            prefs.setGCMReg(regid);
            if (PreferenceKeeper.getInstance().getIsLogin())
                updateGCMID(regid);

        }
    }

    private void updateGCMID(final String regId) {
        AppHttpRequest request = AppRequestBuilder.updateGCMID(regId, new AppResponseListener<CommonResponse>(CommonResponse.class, context) {
            @Override
            public void onSuccess(CommonResponse result) {
                prefs.setGCMReg(regId);
            }

            @Override
            public void onError(ErrorObject error) {

            }
        });

        AppRestClient.getClient().sendRequest(context, request, TAG);
    }
}
