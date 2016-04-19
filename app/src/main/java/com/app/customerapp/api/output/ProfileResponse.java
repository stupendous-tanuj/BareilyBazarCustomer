package com.app.customerapp.api.output;

import java.util.List;

/**
 * Created by umesh on 1/9/16.
 */
public class ProfileResponse {

    private List<CustomerProfile> customerProfile;

    public List<CustomerProfile> getCustomerProfile() {
        return customerProfile;
    }

    public void setCustomerProfile(List<CustomerProfile> customerProfile) {
        this.customerProfile = customerProfile;
    }
}
