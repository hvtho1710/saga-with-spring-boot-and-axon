package com.progressivecoder.ordermanagement.orderservice.queries;

import com.progressivecoder.ecommerce.events.InvoiceBaseEvent;
import com.progressivecoder.ecommerce.events.InvoiceCreatedEvent;
import com.progressivecoder.ecommerce.events.InvoiceRejectedEvent;
import com.progressivecoder.ecommerce.events.OrderBaseEvent;
import com.progressivecoder.ecommerce.events.OrderCreatedEvent;
import com.progressivecoder.ecommerce.events.OrderUpdatedEvent;
import com.progressivecoder.ordermanagement.orderservice.aggregates.ItemType;
import com.progressivecoder.ordermanagement.orderservice.aggregates.OrderAggregate;
import com.progressivecoder.ordermanagement.orderservice.aggregates.OrderStatus;
import com.progressivecoder.ordermanagement.orderservice.entity.OrderEntity;
import com.progressivecoder.ordermanagement.orderservice.repository.OrderRepository;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.eventsourcing.EventSourcingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class OrderQueryEntityManager {

    @Autowired
    private OrderRepository orderRepository;

    @EventHandler
    void on(OrderCreatedEvent event){
        System.out.println("OrderQueryEntityManager::on() --> OrderCreatedEvent");
        persistOrder(buildQueryOrder(event));
    }

    @EventHandler
    void on(OrderUpdatedEvent event){
        System.out.println("OrderQueryEntityManager::on() --> OrderUpdatedEvent");
        persistOrder(buildQueryUpdateOrder(event));
    }

    @EventHandler
    public void handle( InvoiceCreatedEvent event ) {
        System.out.println("OrderQueryEntityManager::on() --> InvoiceCreatedEvent");
        persistOrder(buildQueryUpdatePaymentId(event));
    }

    @EventHandler
    public void handle( InvoiceRejectedEvent event ) {
        System.out.println("OrderQueryEntityManager::on() --> InvoiceRejectedEvent");
        persistOrder(buildQueryUpdatePaymentId(event));
    }

    private OrderEntity findExistingOrCreateQueryOrder(String id){
        return orderRepository.findById(id).isPresent() ? orderRepository.findById(id).get() : new OrderEntity();
    }

    private OrderEntity buildQueryUpdateOrder(OrderUpdatedEvent orderUpdatedEvent){
        OrderEntity orderEntity = findExistingOrCreateQueryOrder(orderUpdatedEvent.getOrderId());

        orderEntity.setOrderStatus(OrderStatus.valueOf(orderUpdatedEvent.getOrderStatus()));

        return orderEntity;
    }

    private OrderEntity buildQueryOrder(OrderCreatedEvent orderCreatedEvent){
        OrderEntity orderEntity = findExistingOrCreateQueryOrder(orderCreatedEvent.getOrderId());

        orderEntity.setOrderId(orderCreatedEvent.getOrderId());
        orderEntity.setCurrency(orderCreatedEvent.getCurrency());
        orderEntity.setItemType(ItemType.valueOf(orderCreatedEvent.getItemType()));
        orderEntity.setOrderStatus(OrderStatus.valueOf(orderCreatedEvent.getOrderStatus()));
        orderEntity.setPrice(orderCreatedEvent.getPrice());

        return orderEntity;
    }

    private OrderEntity buildQueryUpdatePaymentId( InvoiceBaseEvent event ){
        OrderEntity orderEntity = findExistingOrCreateQueryOrder(event.orderId);

        orderEntity.setInvoiceId(event.paymentId);

        return orderEntity;
    }

    private void persistOrder(OrderEntity orderEntity){
        orderRepository.save(orderEntity);
    }
}