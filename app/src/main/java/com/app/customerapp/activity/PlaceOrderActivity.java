package com.app.customerapp.activity;

import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.app.customerapp.R;
import com.app.customerapp.adapter.PlaceOrderAdapter;
import com.app.customerapp.api.input.Cart;
import com.app.customerapp.api.input.Order;
import com.app.customerapp.api.input.OrderRequest;
import com.app.customerapp.api.output.AddressIdentifierResponse;
import com.app.customerapp.api.output.CustomerAddress;
import com.app.customerapp.api.output.ErrorObject;
import com.app.customerapp.api.output.ExpectedDeliveryTimeResponse;
import com.app.customerapp.api.output.PaymentMethod;
import com.app.customerapp.api.output.PlaceOrderResponse;
import com.app.customerapp.api.output.Product;
import com.app.customerapp.constant.AppConstant;
import com.app.customerapp.listner.Catch;
import com.app.customerapp.listner.IDialogListener;
import com.app.customerapp.listner.IProductCatalog;
import com.app.customerapp.network.AppHttpRequest;
import com.app.customerapp.network.AppRequestBuilder;
import com.app.customerapp.network.AppResponseListener;
import com.app.customerapp.network.AppRestClient;
import com.app.customerapp.utils.AppUtil;
import com.app.customerapp.utils.DialogUtils;
import com.app.customerapp.utils.PreferenceKeeper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class PlaceOrderActivity extends CustomerAppBaseActivity {

    private ListView listViewPlaceOrder;
    private TextView tv_place_order_total_quantity;
    private TextView tv_place_order_total_amount;
    private Toolbar toolbar;
    private SwitchCompat switch_place_order;
    private TextView tv_switch_place_order;
    private String timeStampData;
    private String addressIdentifierData;
    private List<Product> products;
    private Order order;
    private OrderRequest orderRequest;
    private List<Order> orders;
    private int totalAmountAdapter;
    private ArrayList<String> categories;
    private ArrayList<String> timeMiliSecond;
    private TextView no_data_available;
    private RelativeLayout root_data;
    private TextView tv_place_order_add_address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_order);
        setUI();
        setUIListner();
        setToolBar();
        getIntentData();
        setSwitcher();
        fetchDeliveryTimeApi();
    }

    @Override
    protected void onStart() {
        super.onStart();
        fetchAddressIdentifierApi();
    }

    private void setSwitcher() {
        tv_switch_place_order.setText("Delivery");
        switch_place_order.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    tv_switch_place_order.setText("Pick up");
                } else {
                    tv_switch_place_order.setText("Delivery");
                }
            }
        });
    }


    private void setUIListner() {
        findViewById(R.id.tv_place_order_continue).setOnClickListener(this);
        findViewById(R.id.tv_place_order_checkout).setOnClickListener(this);
        findViewById(R.id.tv_place_order_expected_delivery_time).setOnClickListener(this);
        findViewById(R.id.tv_place_order_add_address).setOnClickListener(this);
    }

    private void setUI() {
        tv_place_order_add_address = (TextView) findViewById(R.id.tv_place_order_add_address);
        no_data_available = (TextView) findViewById(R.id.no_data_available);
        root_data = (RelativeLayout) findViewById(R.id.root_data);
        tv_switch_place_order = (TextView) findViewById(R.id.tv_switch_place_order);
        switch_place_order = (SwitchCompat) findViewById(R.id.switch_place_order);
        listViewPlaceOrder = (ListView) findViewById(R.id.lv_place_order);
        tv_place_order_total_quantity = (TextView) findViewById(R.id.tv_place_order_total_quantity);
        tv_place_order_total_amount = (TextView) findViewById(R.id.tv_place_order_total_amount);
    }


    private void fetchDeliveryTimeApi() {

        AppHttpRequest request = AppRequestBuilder.fetchDeliveryTimeAPI(new AppResponseListener<ExpectedDeliveryTimeResponse>(ExpectedDeliveryTimeResponse.class, this) {
            @Override
            public void onSuccess(ExpectedDeliveryTimeResponse result) {
                seSpinnerTime(result.getPaymentMethods());
            }

            @Override
            public void onError(ErrorObject error) {
                // showToast(error.getErrorMessage());
            }
        });
        AppRestClient.getClient().sendRequest(this, request, TAG);
    }

    private void seSpinnerTime(List<PaymentMethod> paymentMethods) {
        Spinner spinner = (Spinner) findViewById(R.id.spinner_1);
        categories = new ArrayList<>();
        timeMiliSecond = new ArrayList<>();

        for (PaymentMethod method : paymentMethods) {
            categories.add(AppUtil.getExpectedTimeInHourMinut(Long.parseLong(method.getExpectedDeliveryTime())));
            timeMiliSecond.add(method.getExpectedDeliveryTime());
        }

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                timeStampData = timeMiliSecond.get(pos);
                Log.i("KK", "time : " + categories.get(pos));
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void fetchAddressIdentifierApi() {

        AppHttpRequest request = AppRequestBuilder.fetchDeliveryAddressAPI(new AppResponseListener<AddressIdentifierResponse>(AddressIdentifierResponse.class, this) {
            @Override
            public void onSuccess(AddressIdentifierResponse result) {
                seSpinnerAddressId(result.getCustomerAddresses());
            }

            @Override
            public void onError(ErrorObject error) {
                // showToast(error.getErrorMessage());
            }
        });
        AppRestClient.getClient().sendRequest(this, request, TAG);
    }

    private List<CustomerAddress> customerAddresses;

    private void seSpinnerAddressId(List<CustomerAddress> customerAddresses) {
        Spinner spinner = (Spinner) findViewById(R.id.spinner_2);
        if (customerAddresses == null || customerAddresses.size() == 0) {
            spinner.setVisibility(View.GONE);
            tv_place_order_add_address.setVisibility(View.VISIBLE);
        } else {
            spinner.setVisibility(View.VISIBLE);
            tv_place_order_add_address.setVisibility(View.GONE);

            this.customerAddresses = customerAddresses;

            List<String> categories = new ArrayList<String>();

            for (CustomerAddress address : customerAddresses) {
                categories.add(address.getAddressIdentifier());
            }
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(dataAdapter);

            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                    addressIdentifierData = parent.getItemAtPosition(pos).toString();
                }

                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
        }
    }


    private void setToolBar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView title = (TextView) toolbar.findViewById(R.id.tv_main_title);
        title.setText("Place Order");
        //toolbar.inflateMenu(R.menu.menu_sub_activity);
        toolbar.setNavigationIcon(R.drawable.back_icon);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private void getIntentData() {
        products = PreferenceKeeper.getInstance().getProductData();
        orders = PreferenceKeeper.getInstance().getOrderData();
        if (products == null || orders == null) {
            no_data_available.setVisibility(View.VISIBLE);
            no_data_available.setText("My cart is not available.\nPlease add a cart");
            root_data.setVisibility(View.GONE);
        } else {
            no_data_available.setVisibility(View.GONE);
            root_data.setVisibility(View.VISIBLE);
            order = orders.get(0);
            setPlaceCartAdapter(products);
            setOrderData(order);
        }
    }


    private void setPlaceCartAdapter(List<Product> carts) {
        PlaceOrderAdapter placeOrderlAdapter = new PlaceOrderAdapter(this, carts);
        listViewPlaceOrder.setAdapter(placeOrderlAdapter);

        placeOrderlAdapter.setListener(new IProductCatalog() {
            @Override
            public void finalProductQuantity(int quantity) {
                int totalAmount = 0;
                int totalQuantity = 0;
                for (Product product : products) {
                    if (product.getIncrementQuntity() > 0) {
                        totalQuantity = totalQuantity + product.getIncrementQuntity();
                        totalAmount = totalAmount + product.getIncrementPrize();
                    }
                }
                order.setOrderQuotedQuantity(String.valueOf(totalQuantity));
                order.setOrderQuotedAmount(String.valueOf(totalAmount));

                tv_place_order_total_quantity.setText("Amount (" + String.valueOf(totalQuantity) + ")");
                tv_place_order_total_amount.setText("Total Rs. " + String.valueOf(totalAmount));
            }
        });
    }


    private void setOrderData(Order order) {
        tv_place_order_total_quantity.setText("Amount (" + order.getOrderQuotedQuantity() + ")");
        tv_place_order_total_amount.setText("Total Rs. " + order.getOrderQuotedAmount());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_place_order_continue:
                onBackPressed();
                break;
            case R.id.tv_place_order_checkout:
                placeOrderApi();
                break;
            case R.id.tv_place_order_add_address:
                Bundle bundle = new Bundle();
                bundle.putBoolean(AppConstant.BUNDLE_KEY.IS_ADDRESS_UPDATE_ADAPTER, false);
                launchActivity(AddAddressActivity.class, bundle);
                break;
        }
    }

    private void placeOrderApi() {
        /// check address identif

        if (customerAddresses == null) {
            DialogUtils.showDialogNetwork(this, "You have no address added.\n So please add address.", new IDialogListener() {
                @Override
                public void onClickOk() {
                    launchActivity(AddAddressActivity.class);
                }

                @Override
                public void onCancel() {

                }
            });
        } else {
            ///////////
            if (timeStampData != null) {
                List<String> data = Catch.getInstance().getProductdata();
                order.setOrderPlacedBy(PreferenceKeeper.getInstance().getuserMobileNumber());
                if (data != null && data.get(0) != null)
                    order.setOrderPlacedTo(data.get(0));
                else {
                    order.setOrderPlacedTo("SH1002");
                }
                order.setOrderStatus("Ordered");
                order.setOrderCustomMessage("None");
                order.setOrderDeliveryType(tv_switch_place_order.getText().toString());
                order.setOrderPaymentMethod("COD");
                order.setOrderDeliveryAddressIdentifier(addressIdentifierData);

                // set time stamp

                SimpleDateFormat parser1 = new SimpleDateFormat("dd MMM yyyy HH:mm");
                Calendar calendar = Calendar.getInstance();
                order.setOrderCurrentTime(parser1.format(calendar.getTime()));

                calendar.add(Calendar.MILLISECOND, Integer.parseInt(timeStampData));

                Log.i("KK", "time increment  : " + parser1.format(calendar.getTime()));
                ////////////////
                order.setOrderExpectedDeliveryTimestamp(parser1.format(calendar.getTime()));

                int totalAmount = 0;
                int totalQuantity = 0;
                for (Product product : products) {
                    if (product.getIncrementQuntity() > 0) {
                        totalQuantity = totalQuantity + product.getIncrementQuntity();
                        totalAmount = totalAmount + product.getIncrementPrize();
                    }
                }
                order.setOrderQuotedQuantity(String.valueOf(totalQuantity));
                order.setOrderQuotedAmount(String.valueOf(totalAmount));

                tv_place_order_total_quantity.setText("Amount (" + String.valueOf(totalQuantity) + ")");
                tv_place_order_total_amount.setText("Total Rs. " + String.valueOf(totalAmount));

                orderRequest = new OrderRequest();
                orderRequest.setApplicationId(AppConstant.APPLICATION_ID);
                orderRequest.setUserId(PreferenceKeeper.getInstance().getuserMobileNumber());

                // set cart
                List<Cart> carts = new ArrayList<>();
                for (Product product : products) {
                    if (product.getIncrementQuntity() > 0) {
                        Cart cart = new Cart();
                        cart.setCartProductSKUID(product.getProductSKUID());
                        cart.setCartProductPrice(String.valueOf(product.getProductOfferedPrice()));
                        cart.setCartProductOrderedQuantity(String.valueOf(product.getIncrementQuntity()));
                        carts.add(cart);
                    }
                }

                orderRequest.setCart(carts);
                orderRequest.setOrder(orders);
                if (orderRequest != null && orderRequest.getCart().size() > 0) {
                    showProgressBar();
                    AppHttpRequest request = AppRequestBuilder.placeOrderAPI(orderRequest, new AppResponseListener<PlaceOrderResponse>(PlaceOrderResponse.class, this) {
                        @Override
                        public void onSuccess(PlaceOrderResponse result) {
                            hideProgressBar();
                            showToast("Place Order id  is  :  " + result.getOrderId());
                            if (result != null && result.getOrderId() != null) {
                                PreferenceKeeper.getInstance().setProductData(products);
                                PreferenceKeeper.getInstance().setOrderData(orders);
                                PreferenceKeeper.getInstance().setOrderId(result.getOrderId());
                                launchActivity(OrderDetailActivity.class);
                                finish();
                            }
                        }

                        @Override
                        public void onError(ErrorObject error) {
                            hideProgressBar();

                        }
                    });
                    AppRestClient.getClient().sendRequest(this, request, TAG);
                } else {
                    showToast("At least one product is selected");
                }
            } else {
                showToast("Expected time is not given");
            }
        }
    }
}
