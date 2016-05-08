package com.app.bareillybazarcustomer.listner;

import java.util.List;

/**
 * Created by umesh on 7/1/16.
 */
public class Catch {

    private List<String> productdata;

    private static Catch aCatch;

    public static Catch getInstance() {
        if (aCatch == null) {
            aCatch = new Catch();
        }
        return aCatch;
    }

    public List<String> getProductdata() {
        return productdata;
    }


    public void setProductdata(List<String> productdata) {
        this.productdata = productdata;
    }
}
