package com.app.customerapp.api.output;

public class Product {

    private String productSKUID;
    private String productNameEnglish;
    private String productNameHindi;
    private String productDescription;
    private String productOrderUnit;
    private String productPriceForUnits;
    private String productPrice;
    private String productOfferedPrice;
    private String productImageName;

    /*
      extra
     */

    private int incrementPrize;
    private int incrementQuntity;

    /**
     * @return The productSKUID
     */
    public String getProductSKUID() {
        return productSKUID;
    }

    /**
     * @param productSKUID The productSKUID
     */
    public void setProductSKUID(String productSKUID) {
        this.productSKUID = productSKUID;
    }

    /**
     * @return The productNameEnglish
     */
    public String getProductNameEnglish() {
        return productNameEnglish;
    }

    /**
     * @param productNameEnglish The productNameEnglish
     */
    public void setProductNameEnglish(String productNameEnglish) {
        this.productNameEnglish = productNameEnglish;
    }

    /**
     * @return The productNameHindi
     */
    public String getProductNameHindi() {
        return productNameHindi;
    }

    /**
     * @param productNameHindi The productNameHindi
     */
    public void setProductNameHindi(String productNameHindi) {
        this.productNameHindi = productNameHindi;
    }

    /**
     * @return The productDescription
     */
    public String getProductDescription() {
        return productDescription;
    }

    /**
     * @param productDescription The productDescription
     */
    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    /**
     * @return The productOrderUnit
     */
    public String getProductOrderUnit() {
        return productOrderUnit;
    }

    /**
     * @param productOrderUnit The productOrderUnit
     */
    public void setProductOrderUnit(String productOrderUnit) {
        this.productOrderUnit = productOrderUnit;
    }

    /**
     * @return The productPriceForUnits
     */
    public String getProductPriceForUnits() {
        return productPriceForUnits;
    }

    /**
     * @param productPriceForUnits The productPriceForUnits
     */
    public void setProductPriceForUnits(String productPriceForUnits) {
        this.productPriceForUnits = productPriceForUnits;
    }

    /**
     * @return The productPrice
     */
    public String getProductPrice() {
        return productPrice;
    }

    /**
     * @param productPrice The productPrice
     */
    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    /**
     * @return The productOfferedPrice
     */
    public String getProductOfferedPrice() {
        return productOfferedPrice;
    }

    /**
     * @param productOfferedPrice The productOfferedPrice
     */
    public void setProductOfferedPrice(String productOfferedPrice) {
        this.productOfferedPrice = productOfferedPrice;
    }

    /**
     * @return The productImageName
     */
    public String getProductImageName() {
        return productImageName;
    }

    /**
     * @param productImageName The productImageName
     */
    public void setProductImageName(String productImageName) {
        this.productImageName = productImageName;
    }

    public int getIncrementPrize() {
        return incrementPrize;
    }

    public void setIncrementPrize(int incrementPrize) {
        this.incrementPrize = incrementPrize;
    }

    public int getIncrementQuntity() {
        return incrementQuntity;
    }

    public void setIncrementQuntity(int incrementQuntity) {
        this.incrementQuntity = incrementQuntity;
    }
}