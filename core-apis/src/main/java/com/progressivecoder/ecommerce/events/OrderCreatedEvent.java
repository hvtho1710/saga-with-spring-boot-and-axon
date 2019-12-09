package com.progressivecoder.ecommerce.events;

import java.math.BigDecimal;

public class OrderCreatedEvent {

    public final String orderId;

    public final String itemType;

    public final BigDecimal price;

    public final String currency;

    public final String orderStatus;

    public OrderCreatedEvent(String orderId, String itemType, BigDecimal price, String currency, String orderStatus) {
        this.orderId = orderId;
        this.itemType = itemType;
        this.price = price;
        this.currency = currency;
        this.orderStatus = orderStatus;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getItemType() {
        return itemType;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getCurrency() {
        return currency;
    }

    public String getOrderStatus() {
        return orderStatus;
    }
}
