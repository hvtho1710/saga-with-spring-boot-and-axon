package com.progressivecoder.ecommerce.events;

public class InvoiceCreatedEvent extends InvoiceBaseEvent {

    public InvoiceCreatedEvent(String paymentId, String orderId) {
        super(paymentId, orderId);
    }

}
