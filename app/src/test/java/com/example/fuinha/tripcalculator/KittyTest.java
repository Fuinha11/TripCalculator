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

        assertEquals(new BigDecimal(50), kitty.getTotal());
        assertEquals(new BigDecimal(10), kitty.getTotalPerPerson());
        assertEquals(new BigDecimal(30), kitty.getPayedAmount(person));
    }
}
