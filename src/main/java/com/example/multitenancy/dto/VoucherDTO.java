package com.example.multitenancy.dto;

import java.math.BigDecimal;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VoucherDTO {
    private Integer voucherId;
    private String description;
    private String voucherType;
    private Integer employeeId;
    private String employeeDisplayName; // New field for UI dropdown
    private String invoiceNo;
    private Date invoiceDate;
    private Date voucherDate;
    private BigDecimal Amount;
    private String reason;
}

