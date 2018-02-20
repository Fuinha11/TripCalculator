package com.example.fuinha.tripcalculator.Entities;

import java.math.BigDecimal;

/**
 * Created by Fuinha on 20/02/2018.
 */

public class Transaction {
    private Long transactionId;
    private Person payer;
    private Kitty kitty;
    private BigDecimal value;

    public Transaction(Person payer, Kitty kitty, BigDecimal value) {
        this(0L, payer, kitty, value);
    }

    public Transaction(Long transactionId, Person payer, Kitty kitty, BigDecimal value) {
        this.transactionId = transactionId;
        this.payer = payer;
        this.kitty = kitty;
        this.value = value;
    }

    public Long getTransactionId() {
        return transactionId;
    }

    public Transaction setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
        return this;
    }

    public Person getPayer() {
        return payer;
    }

    public Transaction setPayer(Person payer) {
        this.payer = payer;
        return this;
    }

    public Kitty getKitty() {
        return kitty;
    }

    public Transaction setKitty(Kitty kitty) {
        this.kitty = kitty;
        return this;
    }

    public BigDecimal getValue() {
        return value;
    }

    public Transaction setValue(BigDecimal value) {
        this.value = value;
        return this;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Transaction && ((Transaction) obj).transactionId == this.transactionId;
    }
}
