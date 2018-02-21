package com.example.fuinha.tripcalculator.Entities;

import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * Created by Fuinha on 21/02/2018.
 */

public class PayingPerson {
    private Person person;
    private BigDecimal hasToPay;
    private BigDecimal hasToReceive;
    private ArrayList<Payment> payments;

    public PayingPerson(Person person) {
        this.person = person;
        this.payments = new ArrayList<>();
        generateValues();
    }

    private void generateValues() {
        BigDecimal result = person.getAmountDue();
        if (result.compareTo(BigDecimal.ZERO) < 0){
            hasToPay = new BigDecimal(0);
            hasToReceive = result.abs();
        } else {
            hasToPay = result;
            hasToReceive = new BigDecimal(0);
        }
    }

    public BigDecimal hasToReceive(){
        BigDecimal result = hasToReceive;
        for (Payment pay : payments){
            if (pay.getTo().equals(person))
                result = result.subtract(pay.getAmount());
        }
        return result;
    }

    public BigDecimal hasToPay(){
        BigDecimal result = hasToPay;
        for (Payment pay : payments){
            if (pay.getFrom().equals(person))
                result = result.subtract(pay.getAmount());
        }
        return result;
    }

    public Person getPerson() {
        return person;
    }

    public void pay(Person to, BigDecimal amount){
        payments.add(new Payment(person, to, amount));
    }

    public void receive(Person from, BigDecimal amount){
        payments.add(new Payment(from, person, amount));
    }
}
