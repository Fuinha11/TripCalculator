package com.example.fuinha.tripcalculator;

import com.example.fuinha.tripcalculator.Entities.Kitty;
import com.example.fuinha.tripcalculator.Entities.Person;
import com.example.fuinha.tripcalculator.Entities.Transaction;

import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

/**
 * Created by Fuinha on 20/02/2018.
 */

public class KittyTest {
    private Kitty kitty;

    @Test
    public void performTests(){
        valuesTest();
        paidMostTest();
        paidLessTest();
    }

    private void populate(){
        kitty = new Kitty(69L, "test_kitty");
        for (int i = 0; i < 5; i++) {
            Person user = new Person(Long.valueOf(i), "user" + i);
            kitty.getPeople().add(user);
        }
    }

    @Test
    public void valuesTest(){
        populate();
        Person person = kitty.getPeople().get(0);
        Transaction transaction = new Transaction(person, kitty, new BigDecimal(10));
        kitty.getTransactions().add(transaction);
        kitty.getTransactions().add(transaction);
        kitty.getTransactions().add(transaction);
        Transaction newTransaction = new Transaction(kitty.getPeople().get(1), kitty, new BigDecimal(10));
        kitty.getTransactions().add(newTransaction);
        kitty.getTransactions().add(newTransaction);

        assertEquals(new BigDecimal(50).floatValue(), kitty.getTotal().floatValue(), 0.0);
        assertEquals(new BigDecimal(10).floatValue(), kitty.getTotalPerPerson().floatValue(), 0.0);
        assertEquals(new BigDecimal(30).floatValue(), kitty.getPaidAmount(person).floatValue(), 0.0);
    }

    @Test
    public void paidMostTest(){
        valuesTest();
        Person person = new Person(999L, "lol");
        kitty.getPeople().add(person);

        Transaction transaction = new Transaction(person, kitty, new BigDecimal(100));
        kitty.getTransactions().add(transaction);
        kitty.getTransactions().add(transaction);
        kitty.getTransactions().add(transaction);
        Person newPerson = kitty.getPaidMost();
        assertEquals(person, newPerson);
    }

    @Test
    public void paidLessTest(){
        valuesTest();
        Person person = new Person(999L, "lol");

        for (Person p : kitty.getPeople()) {
            Transaction transaction = new Transaction(p, kitty, new BigDecimal(50));
            kitty.getTransactions().add(transaction);
        }

        kitty.getPeople().add(person);
        Person newPerson = kitty.getPaidLess();
        assertEquals(person, newPerson);

        Transaction transaction = new Transaction(person, kitty, new BigDecimal(10));
        kitty.getTransactions().add(transaction);
        newPerson = kitty.getPaidLess();
        assertEquals(person, newPerson);
    }
}
