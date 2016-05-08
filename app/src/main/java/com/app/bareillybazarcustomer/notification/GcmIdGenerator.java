package com.app.bareillybazarcustomer.notification;

import android.os.AsyncTask;
import android.util.Log;

import com.app.bareillybazarcustomer.activity.BaseActivity;
import com.app.bareillybazarcustomer.activity.HomeActivity;
import com.app.bareillybazarcustomer.api.output.CommonResponse;
import com.app.bareillybazarcustomer.api.output.ErrorObject;
import com.app.bareillybazarcustomer.constant.AppConstant;
import com.app.bareillybazarcustomer.network.AppHttpRequest;
import com.app.bareillybazarcustomer.network.AppRequestBuilder;
import com.app.bareillybazarcustomer.network.AppResponseListener;
import com.app.bareillybazarcustomer.network.AppRestClient;
import com.app.bareillybazarcustomer.utils.PreferenceKeeper;
import com.google.android.gms.gcm.GoogleCloudMessaging;

import java.io.IOException;


public class GcmIdGenerator {

    private String TAG = HomeActivity.class.getSimpleName();
    private final BaseActivity context;
    private final PreferenceKeeper prefs;
    private GoogleCloudMessaging gcm;

    public GcmIdGenerator(BaseActivity context) {
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
            if (PreferenceKeeper.getInstance().getIsLogin()){}
            updateGCMID(regid);
        }
    }

    private void updateGCMID(final String regId) {
        AppHttpRequest request = AppRequestBuilder.updateGCMIdAPI(regId, new AppResponseListener<CommonResponse>(CommonResponse.class, context) {
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
