package com.progressivecoder.ordermanagement.orderservice.entity;

import com.progressivecoder.ordermanagement.orderservice.aggregates.ItemType;
import com.progressivecoder.ordermanagement.orderservice.aggregates.OrderStatus;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "orders")
public class OrderEntity {

    @Id
    @Column(name = "id")
    private String orderId;

    @Enumerated(EnumType.STRING)
    private ItemType itemType;

    private BigDecimal price;

    private String currency;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public ItemType getItemType() {
        return itemType;
    }

    public void setItemType(ItemType itemType) {
        this.itemType = itemType;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }
}
