package com.app.bareillybazarcustomer.api.output;

import java.util.List;

/**
 * Created by umesh on 17/12/15.
 */
public class ShopDetailResponse {

    private List<Shop> shops;

    public List<Shop> getShops() {
        return shops;
    }

    public void setShops(List<Shop> shops) {
        this.shops = shops;
    }
}
