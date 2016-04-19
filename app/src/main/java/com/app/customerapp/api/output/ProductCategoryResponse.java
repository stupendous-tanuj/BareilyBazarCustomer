package com.app.customerapp.api.output;

import java.util.List;

/**
 * Created by umesh on 12/20/15.
 */
public class ProductCategoryResponse {

    private List<ProductCategory> productCategories;

    private String shopId;
    private String shopAddress;
    private String shopAddressAreaSector;
    private String shopAddressCity;
    private String shopLatitude;
    private String shopLongitude;
    private String shopClosingTime;
    private String shopOpeningTime;
    private String shopMinimumAcceptedOrder;
    private String shopDeliveryCharges;
    private String shopDeliveryTypeSupported;


    public List<ProductCategory> getProductCategories() {
        return productCategories;
    }

    public void setProductCategories(List<ProductCategory> productCategories) {
        this.productCategories = productCategories;
    }


    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getShopAddress() {
        return shopAddress;
    }

    public void setShopAddress(String shopAddress) {
        this.shopAddress = shopAddress;
    }

    public String getShopAddressAreaSector() {
        return shopAddressAreaSector;
    }

    public void setShopAddressAreaSector(String shopAddressAreaSector) {
        this.shopAddressAreaSector = shopAddressAreaSector;
    }

    public String getShopAddressCity() {
        return shopAddressCity;
    }

    public void setShopAddressCity(String shopAddressCity) {
        this.shopAddressCity = shopAddressCity;
    }

    public String getShopLatitude() {
        return shopLatitude;
    }

    public void setShopLatitude(String shopLatitude) {
        this.shopLatitude = shopLatitude;
    }

    public String getShopLongitude() {
        return shopLongitude;
    }

    public void setShopLongitude(String shopLongitude) {
        this.shopLongitude = shopLongitude;
    }

    public String getShopClosingTime() {
        return shopClosingTime;
    }

    public void setShopClosingTime(String shopClosingTime) {
        this.shopClosingTime = shopClosingTime;
    }

    public String getShopOpeningTime() {
        return shopOpeningTime;
    }

    public void setShopOpeningTime(String shopOpeningTime) {
        this.shopOpeningTime = shopOpeningTime;
    }

    public String getShopMinimumAcceptedOrder() {
        return shopMinimumAcceptedOrder;
    }

    public void setShopMinimumAcceptedOrder(String shopMinimumAcceptedOrder) {
        this.shopMinimumAcceptedOrder = shopMinimumAcceptedOrder;
    }

    public String getShopDeliveryCharges() {
        return shopDeliveryCharges;
    }

    public void setShopDeliveryCharges(String shopDeliveryCharges) {
        this.shopDeliveryCharges = shopDeliveryCharges;
    }

    public String getShopDeliveryTypeSupported() {
        return shopDeliveryTypeSupported;
    }

    public void setShopDeliveryTypeSupported(String shopDeliveryTypeSupported) {
        this.shopDeliveryTypeSupported = shopDeliveryTypeSupported;
    }
}
