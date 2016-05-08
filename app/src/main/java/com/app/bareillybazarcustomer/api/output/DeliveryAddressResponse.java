package com.app.bareillybazarcustomer.api.output;

import java.util.List;

/**
 * Created by umesh on 5/1/16.
 */
public class DeliveryAddressResponse {

    private List<CustomerAddress> customerAddresses;

    public List<CustomerAddress> getCustomerAddresses() {
        return customerAddresses;
    }

    public void setCustomerAddresses(List<CustomerAddress> customerAddresses) {
        this.customerAddresses = customerAddresses;
    }
}
