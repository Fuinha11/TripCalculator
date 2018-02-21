package com.example.fuinha.tripcalculator.Entities;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Fuinha on 20/02/2018.
 */

public class Kitty {
    private Long kittyId;
    private String name;
    private ArrayList<Person> people = new ArrayList<>();
    private ArrayList<Transaction> transactions = new ArrayList<>();

    public BigDecimal getTotal(){
        BigDecimal result = new BigDecimal(0);
        for (Transaction t:transactions){
            result = result.add(t.getValue());
        }
        return result;
    }

    public BigDecimal getTotalPerPerson(){
        BigDecimal result = getTotal();
        return result.divide(BigDecimal.valueOf(getNumOfPeople()), 2, BigDecimal.ROUND_UP);
    }

    public BigDecimal getPaidAmount(Person person){
        BigDecimal result = new BigDecimal(0);
        for (Transaction t:transactions){
            if (t.getPayer().equals(person))
                result = result.add(t.getValue());
        }
        return result;
    }

    public Person getSomeone() {
        if (people.size() == 0)
            return null;
        else if (people.size() == 1)
            return people.get(0);
        else {
            int index = new Random().nextInt(people.size() - 1);
            return people.get(index);
        }
    }

    public Person getPaidMost() {
        Person result = getSomeone();
        BigDecimal sum = new BigDecimal(0);
        for (Person p : people){
            BigDecimal newSum = new BigDecimal(0);
            for (Transaction t : transactions){
                if (t.getPayer().equals(p))
                    newSum = newSum.add(t.getValue());
            }
            if (newSum.compareTo(sum) > 0) {
                sum = newSum;
                result = p;
            }
        }
        return result;
    }

    public Person getPaidLess() {
        Person result = getSomeone();
        BigDecimal sum = new BigDecimal(10000);
        for (Person p : people){
            BigDecimal newSum = new BigDecimal(0);
            for (Transaction t : transactions){
                if (t.getPayer().equals(p))
                    newSum = newSum.add(t.getValue());
            }
            if (newSum.compareTo(sum) < 0) {
                sum = newSum;
                result = p;
            }
        }
        return result;
    }

    public int getNumOfPeople(){
        return people.size();
    }


    public Kitty(Long kittyId) {
        this.kittyId = kittyId;
    }

    public Kitty(String name) {
        this(0L, name);
    }

    public Kitty(Long kittyId, String name) {
        this(kittyId, name, new ArrayList<Person>(), new ArrayList<Transaction>());
    }

    public Kitty(Long kittyId, String name, ArrayList<Person> people, ArrayList<Transaction> transactions) {
        this.kittyId = kittyId;
        this.name = name;
        this.people = people;
        this.transactions = transactions;
    }

    public Long getKittyId() {
        return kittyId;
    }

    public Kitty setKittyId(Long kittyId) {
        this.kittyId = kittyId;
        return this;
    }

    public String getName() {
        return name;
    }

    public Kitty setName(String name) {
        this.name = name;
        return this;
    }

    public ArrayList<Person> getPeople() {
        return people;
    }

    public Kitty setPeople(ArrayList<Person> people) {
        this.people = people;
        return this;
    }

    public ArrayList<Transaction> getTransactions() {
        return transactions;
    }

    public Kitty setTransactions(ArrayList<Transaction> transactions) {
        this.transactions = transactions;
        return this;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Kitty && ((Kitty) obj).kittyId == this.kittyId;
    }
}
