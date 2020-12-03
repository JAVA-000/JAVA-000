package com.example.demo.model;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class Order implements Serializable {

    private Long id;

    private Long productSnapshotId;

    private String orderNo;

    private BigDecimal totalFee;

    private BigDecimal expressFee;

    private BigDecimal discountFee;

    private BigDecimal realPaymentFee;

    private String payFlowNo;

    private Integer orderStatus;

    private Date orderCreateTime;

    private Date paymentTime;

    private Date expressTime;

    private Integer deleted;

}
