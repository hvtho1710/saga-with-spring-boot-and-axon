package com.progressivecoder.ordermanagement.orderservice.entity;

import com.progressivecoder.ordermanagement.orderservice.aggregates.ItemType;
import com.progressivecoder.ordermanagement.orderservice.aggregates.OrderStatus;

import javax.persistence.*;
import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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

    private String invoiceId;
}
