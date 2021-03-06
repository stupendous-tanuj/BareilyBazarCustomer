package com.app.bareillybazarcustomer.adapter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.app.bareillybazarcustomer.R;
import com.app.bareillybazarcustomer.activity.AddAddressActivity;
import com.app.bareillybazarcustomer.activity.BaseActivity;
import com.app.bareillybazarcustomer.activity.DeliveryAddressActivity;
import com.app.bareillybazarcustomer.api.output.CommonResponse;
import com.app.bareillybazarcustomer.api.output.CustomerAddress;
import com.app.bareillybazarcustomer.api.output.ErrorObject;
import com.app.bareillybazarcustomer.constant.AppConstant;
import com.app.bareillybazarcustomer.listner.IDialogListener;
import com.app.bareillybazarcustomer.network.AppHttpRequest;
import com.app.bareillybazarcustomer.network.AppRequestBuilder;
import com.app.bareillybazarcustomer.network.AppResponseListener;
import com.app.bareillybazarcustomer.network.AppRestClient;
import com.app.bareillybazarcustomer.utils.DialogUtils;
import com.app.bareillybazarcustomer.utils.PreferenceKeeper;

import java.util.List;


public class DeliveryAddressAdapter extends BaseAdapter {

    private final LayoutInflater inflater;
    private BaseActivity activity;
    private List<CustomerAddress> customerAddresses;

    public DeliveryAddressAdapter(BaseActivity activity, List<CustomerAddress> customerAddresses) {
        this.activity = activity;
        inflater = LayoutInflater.from(activity);
        this.customerAddresses = customerAddresses;
    }


    @Override
    public int getCount() {
        return customerAddresses != null ? customerAddresses.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return customerAddresses != null ? customerAddresses.get(position) : null;
    }


    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int pos, View convertView, ViewGroup viewGroup) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.adapter_delivery_address, viewGroup, false);
            holder = new ViewHolder();
            holder.tv_delivery_name = (TextView) convertView.findViewById(R.id.tv_delivery_name);
            holder.tv_delivery_address = (TextView) convertView.findViewById(R.id.tv_delivery_address);
            holder.iv_delivery_address_delete = (TextView) convertView.findViewById(R.id.iv_delivery_address_delete);
            holder.iv_delivery_address_update = (TextView) convertView.findViewById(R.id.iv_delivery_address_update);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        setData(customerAddresses.get(pos), holder, pos);

        return convertView;
    }

    private void setData(final CustomerAddress customerAddress, final ViewHolder holder, final int pos) {

        holder.tv_delivery_name.setText(PreferenceKeeper.getInstance().getuserMobileNumber());
        String address = customerAddress.getAddressIdentifier() + "\n" + customerAddress.getFlatNumberHouseNumber()+ "\n" + customerAddress.getBuildingSocietyStreet() + "\n" + customerAddress.getAreaLocality() + "\n" + customerAddress.getCity() +
                "\n" + customerAddress.getState() + "\n" + customerAddress.getPincode();

        holder.tv_delivery_address.setText(address);

        holder.iv_delivery_address_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DialogUtils.showDialogYesNo(activity, activity.getString(R.string.remove_address_msg), activity.getString(R.string.yes), activity.getString(R.string.no), new IDialogListener() {
                    @Override
                    public void onClickOk() {
                        removeAddressApi(customerAddress.getAddressIdentifier(), pos);
                    }

                    @Override
                    public void onCancel() {

                    }
                });
            }
        });

        holder.iv_delivery_address_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateAddressActivity(customerAddress);
            }
        });

        holder.tv_delivery_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateAddressActivity(customerAddress);
            }
        });
    }


    private void updateAddressActivity(CustomerAddress customerAddress)
    {
        Bundle bundle = new Bundle();
        bundle.putBoolean(AppConstant.BUNDLE_KEY.IS_ADDRESS_UPDATE_ADAPTER, true);
        bundle.putString(AppConstant.BUNDLE_KEY.flatNumberHouseNumber, customerAddress.getFlatNumberHouseNumber());
        bundle.putString(AppConstant.BUNDLE_KEY.addressIdentifier, customerAddress.getAddressIdentifier());
        bundle.putString(AppConstant.BUNDLE_KEY.buildingSocietyStreet, customerAddress.getBuildingSocietyStreet());
        bundle.putString(AppConstant.BUNDLE_KEY.areaLocality, customerAddress.getAreaLocality());
        bundle.putString(AppConstant.BUNDLE_KEY.city, customerAddress.getCity());
        bundle.putString(AppConstant.BUNDLE_KEY.state, customerAddress.getState());
        bundle.putString(AppConstant.BUNDLE_KEY.pincode, customerAddress.getPincode());
        activity.launchActivity(AddAddressActivity.class, bundle);
    }

    private void removeAddressApi(String addressIdentifire, final int pos) {
        activity.showProgressBar();

        AppHttpRequest request = AppRequestBuilder.removeAddressAPI(addressIdentifire, new AppResponseListener<CommonResponse>(CommonResponse.class, activity) {
            @Override
            public void onSuccess(CommonResponse result) {
                activity.hideProgressBar();
                activity.showToast(result.getSuccessMessage());
                customerAddresses.remove(pos);
                if (customerAddresses.size() == 0) {
                    ((DeliveryAddressActivity) activity).setNoData(null);
                }
                notifyDataSetChanged();
            }

            @Override
            public void onError(ErrorObject error) {
                activity.hideProgressBar();

            }
        });
        AppRestClient.getClient().sendRequest((BaseActivity) activity, request, activity.TAG);
    }

    public class ViewHolder {
        private TextView tv_delivery_name;
        private TextView tv_delivery_address;
        private TextView iv_delivery_address_delete;
        private TextView iv_delivery_address_update;

    }
}
