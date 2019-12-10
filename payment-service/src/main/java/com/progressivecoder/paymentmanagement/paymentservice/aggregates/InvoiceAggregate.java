package com.progressivecoder.paymentmanagement.paymentservice.aggregates;

import com.progressivecoder.ecommerce.commands.CreateInvoiceCommand;
import com.progressivecoder.ecommerce.events.InvoiceCreatedEvent;
import com.progressivecoder.ecommerce.events.InvoiceRejectedEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import static java.lang.Thread.sleep;

@Aggregate
public class InvoiceAggregate {

    @AggregateIdentifier
    private String paymentId;

    private String orderId;

    private InvoiceStatus invoiceStatus;

    public InvoiceAggregate() {
    }

    @CommandHandler
    public InvoiceAggregate(CreateInvoiceCommand createInvoiceCommand) throws InterruptedException  {
        System.out.println("InvoiceAggregate::InvoiceAggregate()::@CommandHandler --> CreateInvoiceCommand: " + createInvoiceCommand.toString());
        //sleep(10000);
        if (createInvoiceCommand.price.intValue() > 100000)
            AggregateLifecycle.apply(new InvoiceRejectedEvent(createInvoiceCommand.paymentId, createInvoiceCommand.orderId));
        else
            AggregateLifecycle.apply(new InvoiceCreatedEvent(createInvoiceCommand.paymentId, createInvoiceCommand.orderId));
    }

    @EventSourcingHandler
    protected void on(InvoiceCreatedEvent invoiceCreatedEvent){
        System.out.println("InvoiceAggregate::InvoiceAggregate()::@EventSourcingHandler --> InvoiceCreatedEvent: " + invoiceCreatedEvent.toString());
        this.paymentId = invoiceCreatedEvent.paymentId;
        this.orderId = invoiceCreatedEvent.orderId;
        this.invoiceStatus = InvoiceStatus.PAID;
    }

    @EventSourcingHandler
    protected void on(InvoiceRejectedEvent invoiceRejectedEvent){
        System.out.println("InvoiceAggregate::InvoiceAggregate()::@EventSourcingHandler --> InvoiceRejectedEvent: " + invoiceRejectedEvent.toString());
        this.paymentId = invoiceRejectedEvent.paymentId;
        this.orderId = invoiceRejectedEvent.orderId;
        this.invoiceStatus = InvoiceStatus.PAYMENT_REVERSED;
    }
}
