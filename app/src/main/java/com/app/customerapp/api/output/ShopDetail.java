package com.app.customerapp.api.output;

public class ShopDetail {

    private String shopId;
    private String shopAddress;
    private String shopAddressAreaSector;
    private String shopAddressCity;
    private Integer customerFavroiteShop;

    /**
     * @return The shopId
     */
    public String getShopId() {
        return shopId;
    }

    /**
     * @param shopId The shopId
     */
    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    /**
     * @return The shopAddress
     */
    public String getShopAddress() {
        return shopAddress;
    }

    /**
     * @param shopAddress The shopAddress
     */
    public void setShopAddress(String shopAddress) {
        this.shopAddress = shopAddress;
    }

    /**
     * @return The shopAddressAreaSector
     */
    public String getShopAddressAreaSector() {
        return shopAddressAreaSector;
    }

    /**
     * @param shopAddressAreaSector The shopAddressAreaSector
     */
    public void setShopAddressAreaSector(String shopAddressAreaSector) {
        this.shopAddressAreaSector = shopAddressAreaSector;
    }

    /**
     * @return The shopAddressCity
     */
    public String getShopAddressCity() {
        return shopAddressCity;
    }

    /**
     * @param shopAddressCity The shopAddressCity
     */
    public void setShopAddressCity(String shopAddressCity) {
        this.shopAddressCity = shopAddressCity;
    }

    /**
     * @return The customerFavroiteShop
     */
    public Integer getCustomerFavroiteShop() {
        return customerFavroiteShop;
    }

    /**
     * @param customerFavroiteShop The customerFavroiteShop
     */
    public void setCustomerFavroiteShop(Integer customerFavroiteShop) {
        this.customerFavroiteShop = customerFavroiteShop;
    }


}