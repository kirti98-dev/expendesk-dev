package com.example.multitenancy.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "voucher")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Voucher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "VOUCHER_ID")
    private Integer voucherId;

    @Column(name = "INVOICE_TYPE", nullable = false)
    private String invoiceType; // Voucher Type

    @ManyToOne
    @JoinColumn(name = "EMPLOYEE_ID", referencedColumnName = "EMPLOYEE_ID", nullable = false)
    private Employee employee;

    @Column(name = "INVOICE_NO", nullable = false, length = 50)
    private String invoiceNo;

    @Column(name = "INVOICE_DATE", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date invoiceDate;

    @Column(name = "TOTAL_AMOUNT", nullable = false, precision = 15, scale = 2)
    private BigDecimal totalAmount; // Amount

    @Column(name = "VOUCHER_DATE", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date voucherDate; // Date

    @Column(name = "DESCRIPTION", nullable = false, length = 255)
    private String description; // Description

    @Column(name = "DELETE_STATUS", columnDefinition = "char(1) default 'N'")
    private String deleteStatus = "N"; // Default value 'N'

    @Column(name = "IS_APPROVED", columnDefinition = "char(1)")
    private Character isApproved; // Approval Status (can be NULL)

    @Column(name = "APPROVED_AMOUNT", precision = 15, scale = 2)
    private BigDecimal approvedAmount; // Approved amount
    
    @Column(name = "REASON", nullable = false)
    private String reason;

}
