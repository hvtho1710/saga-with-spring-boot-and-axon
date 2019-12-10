package com.progressivecoder.ecommerce.events;

public class InvoiceRejectedEvent extends InvoiceBaseEvent {


    public InvoiceRejectedEvent(String paymentId, String orderId) {
        super(paymentId, orderId);
    }

}
