package com.app.bareillybazarcustomer.notification;

import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import com.app.bareillybazarcustomer.R;
import com.app.bareillybazarcustomer.activity.MyOrderDetailActivity;
import com.app.bareillybazarcustomer.activity.OrderDetailActivity;
import com.app.bareillybazarcustomer.constant.AppConstant;
import com.app.bareillybazarcustomer.utils.Logger;
import com.google.android.gms.gcm.GoogleCloudMessaging;

public class GcmIntentService extends IntentService {

    private static final String TAG = GcmIntentService.class.getCanonicalName();
    private NotificationCompat.Builder notificationBuilder = null;
    private boolean isChatMode = false;
    private String channelId;

    public GcmIntentService() {
        super("GcmIntentService");
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Bundle extras = intent.getExtras();
        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);
        String messageType = gcm.getMessageType(intent);
        if (!extras.isEmpty()) {
            if (GoogleCloudMessaging.MESSAGE_TYPE_SEND_ERROR.equals(messageType)) {
                sendNotification(getResources().getString(R.string.app_name), extras.toString(), "", "", "" );
            } else if (GoogleCloudMessaging.MESSAGE_TYPE_DELETED.equals(messageType)) {
                sendNotification(getResources().getString(R.string.app_name), extras.toString(), "", "", "");
            } else if (GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE.equals(messageType)) {
                if (extras != null && extras.size() > 0) {
                    //  sendMessageBroadcast(extras.toString(), "");
                    try {
                        String orderId = extras.getString("orderId");
                        String msg = extras.getString("message");
                        String orderStatus = extras.getString("orderStatus");
                        if (msg != null && !msg.equalsIgnoreCase("")) {
                            Logger.INFO("GCM RESPONSE :: ", msg);
//                            JSONObject res = new JSONObject(response);
                            //                          if (res.has("response")) {
                            //JSONObject data = res.getJSONObject("response");
                            //String msg = (String) data.get("message");
                            sendNotification(getResources().getString(R.string.app_name), msg, "", orderId, orderStatus);
                            // sendMessageBroadcast(msg, type);
                            //                        }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        // Release the wake lock provided by the WakefulBroadcastReceiver.
        TestGcmBroadcastReceiver.completeWakefulIntent(intent);
    }


    private void sendMessageBroadcast(String msg, String type) {
        //  BroadcastManager.getInstance().sendEventBroadcast(msg, type);
    }

    private void sendNotification(String title, String msg, String type, String orderId, String orderStatus) {
        // Build intent for notification content
        PendingIntent pendingIntent;
        Bundle eventBundle = new Bundle();
        eventBundle.putString("eventType", type);
        eventBundle.putString(AppConstant.BUNDLE_KEY.ORDER_ID, orderId);
        eventBundle.putString(AppConstant.BUNDLE_KEY.ORDER_STATUS, orderStatus);
        Intent intent = new Intent(this, MyOrderDetailActivity.class);
        intent.putExtras(eventBundle);
        intent.setAction("ACTION");
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.logo)
                .setContentTitle(title)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(msg))
                .setContentIntent(pendingIntent);
        notificationBuilder.setSound(Settings.System.DEFAULT_NOTIFICATION_URI);
        notificationBuilder.setAutoCancel(true);

        // Get an instance of the NotificationManager service
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        // Build the notification and issues it with notification manager.
        notificationManager.notify(1, notificationBuilder.build());
    }
}
