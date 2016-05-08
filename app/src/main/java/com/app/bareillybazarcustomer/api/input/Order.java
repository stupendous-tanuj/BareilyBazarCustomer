package com.app.bareillybazarcustomer.api.input;

public class Order {

    private String orderPlacedBy;
    private String orderPlacedTo;
    private String orderQuotedQuantity;
    private String orderQuotedAmount;
    private String orderDeliveryType;
    private String orderPaymentMethod;
    private String orderStatus;
    private String orderCustomMessage;
    private String orderExpectedDeliveryTimestamp;
    private String orderDeliveryAddressIdentifier;
    private String orderCreationTimestamp;

    /**
     * @return The orderPlacedBy
     */
    public String getOrderPlacedBy() {
        return orderPlacedBy;
    }

    /**
     * @param orderPlacedBy The orderPlacedBy
     */
    public void setOrderPlacedBy(String orderPlacedBy) {
        this.orderPlacedBy = orderPlacedBy;
    }

    /**
     * @return The orderPlacedTo
     */
    public String getOrderPlacedTo() {
        return orderPlacedTo;
    }

    /**
     * @param orderPlacedTo The orderPlacedTo
     */
    public void setOrderPlacedTo(String orderPlacedTo) {
        this.orderPlacedTo = orderPlacedTo;
    }

    /**
     * @return The orderQuotedAmount
     */
    public String getOrderQuotedAmount() {
        return orderQuotedAmount;
    }

    /**
     * @param orderQuotedAmount The orderQuotedAmount
     */
    public void setOrderQuotedAmount(String orderQuotedAmount) {
        this.orderQuotedAmount = orderQuotedAmount;
    }

    /**
     * @return The orderDeliveryType
     */
    public String getOrderDeliveryType() {
        return orderDeliveryType;
    }

    /**
     * @param orderDeliveryType The orderDeliveryType
     */
    public void setOrderDeliveryType(String orderDeliveryType) {
        this.orderDeliveryType = orderDeliveryType;
    }

    /**
     * @return The orderPaymentMethod
     */
    public String getOrderPaymentMethod() {
        return orderPaymentMethod;
    }

    /**
     * @param orderPaymentMethod The orderPaymentMethod
     */
    public void setOrderPaymentMethod(String orderPaymentMethod) {
        this.orderPaymentMethod = orderPaymentMethod;
    }

    /**
     * @return The orderStatus
     */
    public String getOrderStatus() {
        return orderStatus;
    }

    /**
     * @param orderStatus The orderStatus
     */
    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    /**
     * @return The orderCustomMessage
     */
    public String getOrderCustomMessage() {
        return orderCustomMessage;
    }

    /**
     * @param orderCustomMessage The orderCustomMessage
     */
    public void setOrderCustomMessage(String orderCustomMessage) {
        this.orderCustomMessage = orderCustomMessage;
    }

    /**
     * @return The orderExpectedDeliveryTimestamp
     */
    public String getOrderExpectedDeliveryTimestamp() {
        return orderExpectedDeliveryTimestamp;
    }

    /**
     * @param orderExpectedDeliveryTimestamp The orderExpectedDeliveryTimestamp
     */
    public void setOrderExpectedDeliveryTimestamp(String orderExpectedDeliveryTimestamp) {
        this.orderExpectedDeliveryTimestamp = orderExpectedDeliveryTimestamp;
    }

    /**
     * @return The orderDeliveryAddressIdentifier
     */
    public String getOrderDeliveryAddressIdentifier() {
        return orderDeliveryAddressIdentifier;
    }

    /**
     * @param orderDeliveryAddressIdentifier The orderDeliveryAddressIdentifier
     */
    public void setOrderDeliveryAddressIdentifier(String orderDeliveryAddressIdentifier) {
        this.orderDeliveryAddressIdentifier = orderDeliveryAddressIdentifier;
    }

    public String getOrderQuotedQuantity() {
        return orderQuotedQuantity;
    }

    public void setOrderQuotedQuantity(String orderQuotedQuantity) {
        this.orderQuotedQuantity = orderQuotedQuantity;
    }

    public String getOrderCurrentTime() {
        return orderCreationTimestamp;
    }

    public void setOrderCurrentTime(String orderCurrentTime) {
        this.orderCreationTimestamp = orderCurrentTime;
    }
}