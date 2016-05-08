package com.app.bareillybazarcustomer.api.output;

import java.util.List;

public class ShopCategoryResponse {

    private List<ShopCategory> shopCategories;

    /**
     * @return The shopCategories
     */
    public List<ShopCategory> getShopCategories() {
        return shopCategories;
    }

    /**
     * @param shopCategories The shopCategories
     */
    public void setShopCategories(List<ShopCategory> shopCategories) {
        this.shopCategories = shopCategories;
    }


}