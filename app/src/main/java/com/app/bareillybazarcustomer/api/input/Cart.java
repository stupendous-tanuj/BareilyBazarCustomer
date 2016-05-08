package com.app.bareillybazarcustomer.api.input;

/**
 * Created by umesh on 15/1/16.
 */
public class Cart {

    private String cartProductSKUID;
    private String cartProductPrice;
    private String cartProductOrderedQuantity;

    public String getCartProductSKUID() {
        return cartProductSKUID;
    }

    public void setCartProductSKUID(String cartProductSKUID) {
        this.cartProductSKUID = cartProductSKUID;
    }

    public String getCartProductPrice() {
        return cartProductPrice;
    }

    public void setCartProductPrice(String cartProductPrice) {
        this.cartProductPrice = cartProductPrice;
    }

    public String getCartProductOrderedQuantity() {
        return cartProductOrderedQuantity;
    }

    public void setCartProductOrderedQuantity(String cartProductOrderedQuantity) {
        this.cartProductOrderedQuantity = cartProductOrderedQuantity;
    }
}
