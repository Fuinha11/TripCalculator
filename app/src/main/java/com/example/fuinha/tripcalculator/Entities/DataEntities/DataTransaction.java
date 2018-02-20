package com.example.fuinha.tripcalculator.Entities.DataEntities;

import java.math.BigDecimal;

/**
 * Created by Fuinha on 20/02/2018.
 */

public class DataTransaction {
    private Long transactionId;
    private Long payerId;
    private Long kittyId;
    private BigDecimal value;

    public DataTransaction(Long transactionId) {
        this(transactionId, null, null, null);
    }

    public DataTransaction(Long transactionId, Long payerId, Long kittyId, BigDecimal value) {
        this.transactionId = transactionId;
        this.payerId = payerId;
        this.kittyId = kittyId;
        this.value = value;
    }

    public Long getTransactionId() {
        return transactionId;
    }

    public DataTransaction setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
        return this;
    }

    public Long getPayerId() {
        return payerId;
    }

    public DataTransaction setPayerId(Long payerId) {
        this.payerId = payerId;
        return this;
    }

    public Long getKittyId() {
        return kittyId;
    }

    public DataTransaction setKittyId(Long kittyId) {
        this.kittyId = kittyId;
        return this;
    }

    public BigDecimal getValue() {
        return value;
    }

    public DataTransaction setValue(BigDecimal value) {
        this.value = value;
        return this;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof DataTransaction && ((DataTransaction) obj).transactionId == transactionId;
    }
}
