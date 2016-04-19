package com.app.customerapp.api.output;

import java.util.List;

/**
 * Created by umesh on 12/20/15.
 */
public class ProductCatelogResponse {
    private List<Product> products;

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}



