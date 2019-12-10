package com.progressivecoder.ordermanagement.orderservice.aggregates;

import com.progressivecoder.ecommerce.commands.CreateOrderCommand;

import com.progressivecoder.ecommerce.commands.UpdateOrderStatusCommand;
import com.progressivecoder.ecommerce.events.OrderCreatedEvent;
import com.progressivecoder.ecommerce.events.OrderUpdatedEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import java.math.BigDecimal;

@Aggregate
public class OrderAggregate {

    @AggregateIdentifier
    private String orderId;

    private ItemType itemType;

    private BigDecimal price;

    private String currency;

    private OrderStatus orderStatus;

    public OrderAggregate() {
    }

    @CommandHandler
    public OrderAggregate(CreateOrderCommand createOrderCommand){
        System.out.println("OrderAggregate::OrderAggregate()::@CommandHandler --> CreateOrderCommand");
        AggregateLifecycle.apply(new OrderCreatedEvent(createOrderCommand.orderId, createOrderCommand.itemType,
                createOrderCommand.price, createOrderCommand.currency, createOrderCommand.orderStatus));
    }

    @EventSourcingHandler
    protected void on(OrderCreatedEvent orderCreatedEvent){
        System.out.println("OrderAggregate::OrderAggregate()::@EventSourcingHandler --> OrderCreatedEvent: " + orderCreatedEvent.toString());
        this.orderId = orderCreatedEvent.orderId;
        this.itemType = ItemType.valueOf(orderCreatedEvent.itemType);
        this.price = orderCreatedEvent.price;
        this.currency = orderCreatedEvent.currency;
        this.orderStatus = OrderStatus.valueOf(orderCreatedEvent.orderStatus);
    }

    @CommandHandler
    protected void on(UpdateOrderStatusCommand updateOrderStatusCommand){
        System.out.println("OrderAggregate::OrderAggregate()::@CommandHandler --> UpdateOrderStatusCommand: " + updateOrderStatusCommand.toString());
        AggregateLifecycle.apply(new OrderUpdatedEvent(updateOrderStatusCommand.orderId, updateOrderStatusCommand.orderStatus));
    }

    @EventSourcingHandler
    protected void on(OrderUpdatedEvent orderUpdatedEvent){
        System.out.println("OrderAggregate::OrderAggregate()::@EventSourcingHandler --> OrderUpdatedEvent:" + orderUpdatedEvent.toString());
        this.orderId = orderId;
        this.orderStatus = OrderStatus.valueOf(orderUpdatedEvent.orderStatus);
    }
}
