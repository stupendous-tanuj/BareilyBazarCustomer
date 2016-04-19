package com.app.customerapp.api.output;

import java.util.List;

/**
 * Created by umesh on 1/10/16.
 */
public class AddressIdentifierResponse {
    private List<CustomerAddress> customerAddresses;

    public List<CustomerAddress> getCustomerAddresses() {
        return customerAddresses;
    }

    public void setCustomerAddresses(List<CustomerAddress> customerAddresses) {
        this.customerAddresses = customerAddresses;
    }
}
