package com.progressivecoder.paymentmanagement.paymentservice.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

import com.progressivecoder.paymentmanagement.paymentservice.aggregates.InvoiceStatus;

@Getter
@Setter
@Entity
@Table(name = "invoices")
public class InvoiceEntity {

    @Id
    @Column(name = "id")
    private String invoiceId;

    private String orderId;

    @Enumerated( EnumType.STRING )
    private InvoiceStatus status;
}
