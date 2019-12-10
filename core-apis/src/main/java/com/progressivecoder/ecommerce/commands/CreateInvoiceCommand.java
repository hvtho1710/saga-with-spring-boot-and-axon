package com.progressivecoder.ecommerce.commands;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.math.BigDecimal;

public class CreateInvoiceCommand{

    @TargetAggregateIdentifier
    public final String paymentId;

    public final String orderId;

    public final BigDecimal price;

    public CreateInvoiceCommand(String paymentId, String orderId, BigDecimal price) {
        this.paymentId = paymentId;
        this.orderId = orderId;
        this.price = price;
    }
}
