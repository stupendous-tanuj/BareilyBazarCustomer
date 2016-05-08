package com.app.bareillybazarcustomer.activity;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.app.bareillybazarcustomer.R;
import com.app.bareillybazarcustomer.api.output.ErrorObject;
import com.app.bareillybazarcustomer.api.output.LoginResponse;
import com.app.bareillybazarcustomer.constant.AppConstant;
import com.app.bareillybazarcustomer.network.AppHttpRequest;
import com.app.bareillybazarcustomer.network.AppRequestBuilder;
import com.app.bareillybazarcustomer.network.AppResponseListener;
import com.app.bareillybazarcustomer.network.AppRestClient;
import com.app.bareillybazarcustomer.network.LocationResponseListener;
import com.app.bareillybazarcustomer.utils.GPSTracker;
import com.app.bareillybazarcustomer.utils.Logger;
import com.app.bareillybazarcustomer.utils.PreferenceKeeper;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class UpdateLocationActivity extends BaseActivity {

    private TextView tv_current_location;
    private boolean isLoginActivity;
    private ListView listSearch;
    private AutoCompleteTextView myAutoComplete;
    private String finalSearchAddress;
    private TextView tv_location_update_click;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_location);
        setUI();
        getIntentData();
        setUIListener();
        setCurrentLocation("");
    }

    private void setUI() {
        tv_location_update_click = (TextView)findViewById(R.id.tv_location_update_click);
        tv_current_location = (TextView) findViewById(R.id.tv_current_location);
        myAutoComplete = (AutoCompleteTextView) findViewById(R.id.auto_complete_locaion);
        listSearch = (ListView) findViewById(R.id.list_location_search);
        setData();
        //view.findViewById(R.id.linearlayout_location).setOnClickListener(this);
    }

    private void setUIListener() {
        findViewById(R.id.tv_use_current_location).setOnClickListener(this);
        findViewById(R.id.tv_location_update_click).setOnClickListener(this);
    }

    private void setCurrentLocation(String finalSearchAddress) {
        if ("".equals(finalSearchAddress)) {
            String flatNumber = PreferenceKeeper.getInstance().getflatNumber();
            String area = PreferenceKeeper.getInstance().getArea();
            String locality = PreferenceKeeper.getInstance().getLocality();
            String city = PreferenceKeeper.getInstance().getCity();
            String state = PreferenceKeeper.getInstance().getState();
            String pincode = PreferenceKeeper.getInstance().getPincode();
            tv_current_location.setText(flatNumber + " " + area + " " + locality + " " + city + " " + state + " " + pincode);
        } else {
            tv_current_location.setText(finalSearchAddress);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_location_update_click:
                getLocationFromAddress(UpdateLocationActivity.this, finalSearchAddress);
                break;
            case R.id.tv_use_current_location:
                updateCurrentLocation();
                break;
        }
    }

    ////////////////////////


    private void setData() {
        myAutoComplete.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Logger.INFO("TAG", "LOCATION DATA : " + charSequence.toString());
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // Logger.INFO("TAG", "LOCATION DATA : " + editable.toString());
                getLocationsBasedOnString(editable.toString());
            }
        });

    }

    private void getLocationsBasedOnString(String writeDate) {

        String key = "key=AIzaSyAyNs0KdHVAhgdtLzl7yEzFmBGH846RqPU";   // Obtain browser key from https://code.google.com/apis/console
        String input = "input=" + URLEncoder.encode(writeDate);
        String types = "types=geocode";    // place type to be searched
        String sensor = "sensor=false";    // Sensor enabled
        String parameters = input + "&" + types + "&" + sensor + "&" + key;   // Building the parameters to the web service
        String output = "json";           // Output format
        String url = output + "?" + parameters;     // Building the url to the web service

        AppHttpRequest request = AppRequestBuilder.getLocationsBasedOnString(url, new LocationResponseListener<ArrayList>(ArrayList.class, this) {
            @Override
            public void onSuccess(final ArrayList list) {
                LocationSearchAdapter locationSearchAdapter = new LocationSearchAdapter(UpdateLocationActivity.this, list);
                listSearch.setAdapter(locationSearchAdapter);
                locationSearchAdapter.notifyDataSetChanged();
                listSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {


                    @Override
                    public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                        myAutoComplete.setText(list.get(position).toString());
                        finalSearchAddress = list.get(position).toString();
                        setCurrentLocation(finalSearchAddress);
                        hideSoftKeyboard();
                    }
                });
            }

            @Override
            public void onError(ErrorObject error) {
            }
        });
        AppRestClient.getClient().sendRequest(this, request, TAG);
    }


    public class LocationSearchAdapter extends BaseAdapter {

        private final LayoutInflater inflater;
        private Context context;
        private ViewHolder viewHolder;
        private List<String> data;

        public LocationSearchAdapter(Context ctx, List<String> data) {
            context = ctx;
            this.data = data;
            inflater = LayoutInflater.from(context);
        }


        @Override
        public int getCount() {
            return data != null ? data.size() : 0;
        }

        @Override
        public Object getItem(int position) {
            return data != null ? data.get(position) : null;
        }


        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int pos, View convertView, ViewGroup viewGroup) {
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.adapter_location_search, viewGroup, false);
                viewHolder = new ViewHolder();
                viewHolder.tv_location_search = (TextView) convertView.findViewById(R.id.tv_location_search);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            setData(viewHolder, (String) getItem(pos));
            return convertView;
        }

        private void setData(ViewHolder viewHolder, String item) {
            viewHolder.tv_location_search.setText(item);
        }

        public class ViewHolder {
            TextView tv_location_search;
        }
    }

    ////////////////////////////////////////
    private void getIntentData() { // only from loginctivity other wise is flse like MyProfile, EditLoction
        if (getIntent() != null && getIntent().getExtras() != null) {
            isLoginActivity = getIntent().getExtras().getBoolean(AppConstant.BUNDLE_KEY.LOC_UPDTE_LOGIN);
            tv_location_update_click.setText("Add Location");
            setToolBarTitle("Add Location");
        } else {
            tv_location_update_click.setText("Update Location");
            setToolBarTitle("Update Location");
        }
    }

    private class UpdateLocationAsy extends AsyncTask<Void, Void, Address> {

        private BaseActivity activity;
        private GPSTracker gps;

        public UpdateLocationAsy(BaseActivity activity, GPSTracker gps) {
            this.activity = activity;
            this.gps = gps;

            showProgressBar();
        }

        @Override
        protected Address doInBackground(Void... params) {
            if (gps != null) {
                double latitude = gps.getLatitude();
                double longit = gps.getLongitude();
                return getAddressFromLocation(latitude, longit);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Address returnedAddress) {
            if (returnedAddress != null) {
                if (isLoginActivity) {
                    Logger.INFO(TAG, "from login");
                    setUserAddress(returnedAddress);
                    hideProgressBar();
                    UpdateLocationActivity.this.finish();
                } else {
                    Logger.INFO(TAG, "from up");
                    updateUserDetailApi(returnedAddress);
                }
            } else {
                showToast("Address is not found");
            }
        }
    }

    private void setUserAddress(Address returnedAddress) {
        String flatNumber = returnedAddress.getFeatureName();
        String area = returnedAddress.getSubAdminArea();
        String locality = returnedAddress.getSubLocality();
        String city = returnedAddress.getLocality();
        String state = returnedAddress.getAdminArea();
        String pincode = returnedAddress.getPostalCode();
        String lat = String.valueOf(returnedAddress.getLatitude());
        String longitude = String.valueOf(returnedAddress.getLongitude());

        PreferenceKeeper.getInstance().setflatNumber(flatNumber);
        PreferenceKeeper.getInstance().setArea(area);
        PreferenceKeeper.getInstance().setLocality(locality);
        PreferenceKeeper.getInstance().setCity(city);
        PreferenceKeeper.getInstance().setState(state);
        PreferenceKeeper.getInstance().setPincode(pincode);
        PreferenceKeeper.getInstance().setLatitute(String.valueOf(lat));
        PreferenceKeeper.getInstance().setLong(String.valueOf(longitude));

    }

    private void updateCurrentLocation() {
        GPSTracker gps = new GPSTracker(this);
        if (gps.canGetLocation()) {
            UpdateLocationAsy runner = new UpdateLocationAsy(this, gps);
            runner.execute();
        } else {
            gps.showSettingsAlert();
        }
    }

    public Address getAddressFromLocation(double lat, double lon) {
        Geocoder geocoder = new Geocoder(this, Locale.ENGLISH);
        try {
            List<Address> addresses = geocoder.getFromLocation(lat, lon, 1);
            if (addresses != null) {
                if (addresses.size() > 0) {
                    Address returnedAddress = addresses.get(0);
                    return returnedAddress;
                }
            } else {
                return null;
            }
        } catch (IOException e) {
            return null;
        }
        return null;
    }


    private void updateUserDetailApi(Address returnedAddress) {
        final String name = PreferenceKeeper.getInstance().getName();
        final String email = PreferenceKeeper.getInstance().getEmail().trim();

        final String flatNumber = returnedAddress.getFeatureName();
        final String area = returnedAddress.getSubAdminArea();
        final String locality = returnedAddress.getSubLocality();
        final String city = returnedAddress.getLocality();
        final String state = returnedAddress.getAdminArea();
        final String pincode = returnedAddress.getPostalCode();
        final String latitute = String.valueOf(returnedAddress.getLatitude());
        final String longitude = String.valueOf(returnedAddress.getLongitude());

        AppHttpRequest request = AppRequestBuilder.updateProfileData(name, email, flatNumber, area, pincode, city, state, latitute, longitude,
                new AppResponseListener<LoginResponse>(LoginResponse.class, this) {
                    @Override
                    public void onSuccess(LoginResponse result) {
                        showToast("Your location successfuly update...");
                        PreferenceKeeper.getInstance().setEmail(email);
                        PreferenceKeeper.getInstance().setName(name);
                        PreferenceKeeper.getInstance().setflatNumber(flatNumber);
                        PreferenceKeeper.getInstance().setArea(area);
                        PreferenceKeeper.getInstance().setLocality(locality);
                        PreferenceKeeper.getInstance().setCity(city);
                        PreferenceKeeper.getInstance().setState(state);
                        PreferenceKeeper.getInstance().setPincode(pincode);
                        PreferenceKeeper.getInstance().setLatitute(String.valueOf(latitute));
                        PreferenceKeeper.getInstance().setLong(String.valueOf(longitude));
                        PreferenceKeeper.getInstance().setIsLogin(true);
                        hideProgressBar();
                        UpdateLocationActivity.this.finish();
                    }

                    @Override
                    public void onError(ErrorObject error) {
                        hideProgressBar();
                    }
                });
        AppRestClient.getClient().sendRequest(this, request, TAG);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void getLocationFromAddress(Context context, String strAddress) {
        Geocoder coder = new Geocoder(context);
        List<Address> address;
        try {
            address = coder.getFromLocationName(strAddress, 5);
            if (address == null) {
                return;
            }
            if (address.size() > 0) {
                Address location = address.get(0);
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();
                SearchLocationAsy runner = new SearchLocationAsy(this, latitude, longitude);
                runner.execute();
            } else {
                showToast("Address is not find");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private class SearchLocationAsy extends AsyncTask<Void, Void, Address> {

        private double latitude;
        private double longitute;

        public SearchLocationAsy(BaseActivity activity, double latitude, double longitute) {
            this.latitude = latitude;
            this.longitute = longitute;
            showProgressBar();
        }

        @Override
        protected Address doInBackground(Void... params) {
            return getAddressFromLocation(latitude, longitute);
        }

        @Override
        protected void onPostExecute(Address returnedAddress) {
            {
                if (returnedAddress != null) {
                    if (isLoginActivity) {
                        Logger.INFO(TAG, "from login");
                        setUserAddress(returnedAddress);
                        hideProgressBar();
                        UpdateLocationActivity.this.finish();
                    } else {
                        Logger.INFO(TAG, "from up");
                        updateUserDetailApi(returnedAddress);
                    }
                } else {
                    showToast("Address is not found");
                }
            }
        }
    }
}
