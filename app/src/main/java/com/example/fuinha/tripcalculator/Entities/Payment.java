package com.example.fuinha.tripcalculator.Entities;

import java.math.BigDecimal;

/**
 * Created by Fuinha on 21/02/2018.
 */

public class Payment {
    private Person from;
    private Person to;
    private BigDecimal amount;

    public Payment(Person from, Person to, BigDecimal amount) {
        this.from = from;
        this.to = to;
        this.amount = amount;
    }

    public Person getFrom() {
        return from;
    }

    public Payment setFrom(Person from) {
        this.from = from;
        return this;
    }

    public Person getTo() {
        return to;
    }

    public Payment setTo(Person to) {
        this.to = to;
        return this;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public Payment setAmount(BigDecimal amount) {
        this.amount = amount;
        return this;
    }

    @Override
    public String toString() {
        return new StringBuilder().append("amount: ").append(amount)
                .append(", from: ").append(from.getPersonId())
                .append(", to: ").append(to.getPersonId()).toString();
    }
}
