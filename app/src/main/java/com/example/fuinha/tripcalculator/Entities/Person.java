package com.example.fuinha.tripcalculator.Entities;

import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * Created by Fuinha on 20/02/2018.
 */

public class Person {
    private Long personId;
    private String name;
    private ArrayList<Kitty> kitties = new ArrayList<>();

    public BigDecimal getPaidTotal(){
        BigDecimal result = new BigDecimal(0);
        for (Kitty k:kitties){
            result = result.add(k.getPaidAmount(this));
        }
        return result;
    }

    public BigDecimal getDebtTotal() {
        BigDecimal result = new BigDecimal(0);
        for (Kitty k:kitties){
            result = result.add(k.getTotalPerPerson());
        }
        return result;
    }

    public BigDecimal getAmountDue() {
        return getDebtTotal().subtract(getPaidTotal());
    }

    public Person(String name) {
        this(0L, name);
    }

    public Person(Long personId, String name) {
        this.personId = personId;
        this.name = name;
    }

    public Long getPersonId() {
        return personId;
    }

    public Person setPersonId(Long personId) {
        this.personId = personId;
        return this;
    }

    public String getName() {
        return name;
    }

    public Person setName(String name) {
        this.name = name;
        return this;
    }

    public ArrayList<Kitty> getKitties() {
        return kitties;
    }

    public Person setKitties(ArrayList<Kitty> kitties) {
        this.kitties = kitties;
        return this;
    }

    public ArrayList<Transaction> getTransactions(){
        ArrayList<Transaction> result = new ArrayList<>();
        ArrayList<Kitty> list = getKitties();

        for (Kitty k : list){
            for (Transaction t : k.getTransactions()){
                if (t.getPayer().equals(this))
                    result.add(t);
            }
        }

        return result;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Person && ((Person) obj).personId == this.personId;
    }

    @Override
    public String toString() {
        return new StringBuilder().append("id: ").append(personId)
                .append(", name: ").append(name).toString();
    }
}
