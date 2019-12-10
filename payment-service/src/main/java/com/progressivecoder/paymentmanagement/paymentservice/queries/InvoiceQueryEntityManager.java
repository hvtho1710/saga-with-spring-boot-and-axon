package com.progressivecoder.paymentmanagement.paymentservice.queries;

import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.progressivecoder.ecommerce.events.InvoiceCreatedEvent;
import com.progressivecoder.ecommerce.events.InvoiceRejectedEvent;
import com.progressivecoder.ecommerce.events.OrderCreatedEvent;
import com.progressivecoder.ecommerce.events.OrderShippedEvent;
import com.progressivecoder.ecommerce.events.OrderUpdatedEvent;
import com.progressivecoder.paymentmanagement.paymentservice.aggregates.InvoiceStatus;
import com.progressivecoder.paymentmanagement.paymentservice.entity.InvoiceEntity;
import com.progressivecoder.paymentmanagement.paymentservice.repository.InvoiceRepository;

@Component
public class InvoiceQueryEntityManager {

    @Autowired
    private InvoiceRepository paymentRepository;

    @EventHandler
    void on( OrderCreatedEvent event){
        System.out.println("InvoiceQueryEntityManager::on() --> OrderCreatedEvent");
    }

    @EventHandler
    void on( OrderShippedEvent event){
        System.out.println("InvoiceQueryEntityManager::on() --> OrderShippedEvent");
    }

    @EventHandler
    void on( OrderUpdatedEvent event){
        System.out.println("InvoiceQueryEntityManager::on() --> OrderUpdatedEvent");
    }

    @EventHandler
    void on( InvoiceCreatedEvent event){
        System.out.println("InvoiceQueryEntityManager::on() --> InvoiceCreatedEvent");
        persistOrder(buildQueryInvoice(event));
    }

    @EventHandler
    void on( InvoiceRejectedEvent event){
        System.out.println("InvoiceQueryEntityManager::on() --> InvoiceRejectedEvent");
        persistOrder(buildQueryInvoice(event));
    }

    private InvoiceEntity findExistingOrCreateQueryOrder(String invoiceId, String orderId) {
        if (paymentRepository.findByOrderId(orderId).isPresent())
            return null;

        return paymentRepository.findById(invoiceId).isPresent() ? paymentRepository.findById(invoiceId).get() : new InvoiceEntity();
    }

    private InvoiceEntity buildQueryInvoice(InvoiceRejectedEvent invoiceRejectedEvent){
        InvoiceEntity invoiceEntity = findExistingOrCreateQueryOrder(invoiceRejectedEvent.paymentId, invoiceRejectedEvent.orderId);

        invoiceEntity.setOrderId(invoiceRejectedEvent.orderId);
        invoiceEntity.setInvoiceId(invoiceRejectedEvent.paymentId);
        invoiceEntity.setStatus( InvoiceStatus.PAYMENT_REVERSED );
        return invoiceEntity;
    }

    private InvoiceEntity buildQueryInvoice(InvoiceCreatedEvent invoiceCreatedEvent){
        InvoiceEntity invoiceEntity = findExistingOrCreateQueryOrder(invoiceCreatedEvent.paymentId, invoiceCreatedEvent.orderId);

        if(invoiceEntity != null) {
            invoiceEntity.setOrderId( invoiceCreatedEvent.orderId );
            invoiceEntity.setInvoiceId( invoiceCreatedEvent.paymentId );
            invoiceEntity.setStatus( InvoiceStatus.PAID );
        }

        return invoiceEntity;
    }

    private void persistOrder( InvoiceEntity invoiceEntity ){
        if(invoiceEntity != null)
            paymentRepository.save( invoiceEntity );
    }
}