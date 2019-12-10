package com.progressivecoder.ecommerce.events;

public class InvoiceBaseEvent {

    public final String paymentId;

    public final String orderId;

    public InvoiceBaseEvent(String paymentId, String orderId) {
	this.paymentId = paymentId;
	this.orderId = orderId;
    }

    public String toString() {
	return "paymentId: " + paymentId + " - orderId: " + orderId + "\n";
    }
}
