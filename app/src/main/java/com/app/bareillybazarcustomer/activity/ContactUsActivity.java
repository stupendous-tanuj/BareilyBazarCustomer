package com.app.bareillybazarcustomer.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.app.bareillybazarcustomer.R;
import com.app.bareillybazarcustomer.api.output.CommonResponse;
import com.app.bareillybazarcustomer.api.output.ErrorObject;
import com.app.bareillybazarcustomer.network.AppHttpRequest;
import com.app.bareillybazarcustomer.network.AppRequestBuilder;
import com.app.bareillybazarcustomer.network.AppResponseListener;
import com.app.bareillybazarcustomer.network.AppRestClient;
import com.app.bareillybazarcustomer.utils.PreferenceKeeper;


public class ContactUsActivity extends BaseActivity {

    private TextView tv_contact_us_mobile_number;
    private EditText et_contact_us_message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);
        setUI();
        setToolBarTitle(getString(R.string.title_Contact_Us));
        setUIListener();
    }

    private void setUIListener() {
        findViewById(R.id.tv_contact_us_send).setOnClickListener(this);
    }

    private void setUI() {
        tv_contact_us_mobile_number = (TextView) findViewById(R.id.tv_contact_us_mobile_number);
        et_contact_us_message = (EditText) findViewById(R.id.et_contact_us_message);

        tv_contact_us_mobile_number.setText(PreferenceKeeper.getInstance().getuserMobileNumber());

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_contact_us_send:
                contactUsSendMessageApi();
                break;
        }
    }

    private void contactUsSendMessageApi() {
        String message = et_contact_us_message.getText().toString().trim();
        showProgressBar();
        AppHttpRequest request = AppRequestBuilder.contactUsSendMessageApi(message, new AppResponseListener<CommonResponse>(CommonResponse.class, this) {
            @Override
            public void onSuccess(CommonResponse result) {
                hideProgressBar();
                showToast(result.getSuccessMessage());
                ContactUsActivity.this.finish();
            }

            @Override
            public void onError(ErrorObject error) {
                hideProgressBar();
            }
        });
        AppRestClient.getClient().sendRequest(this, request, TAG);
    }
}
