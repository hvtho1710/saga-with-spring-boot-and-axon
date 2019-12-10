package com.progressivecoder.ecommerce.events;

public class InvoiceRejectedEvent {

    public final String paymentId;

    public final String orderId;

    public InvoiceRejectedEvent(String paymentId, String orderId) {
        this.paymentId = paymentId;
        this.orderId = orderId;
    }
}
