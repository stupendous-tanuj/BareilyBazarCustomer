package com.app.customerapp.api.output;

import java.util.List;

/**
 * Created by umesh on 1/10/16.
 */
public class ExpectedDeliveryTimeResponse {

    private List<PaymentMethod> paymentMethods;

    public List<PaymentMethod> getPaymentMethods() {
        return paymentMethods;
    }

    public void setPaymentMethods(List<PaymentMethod> paymentMethods) {
        this.paymentMethods = paymentMethods;
    }
}
