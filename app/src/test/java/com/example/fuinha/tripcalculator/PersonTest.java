package com.example.fuinha.tripcalculator;

import com.example.fuinha.tripcalculator.Entities.Kitty;
import com.example.fuinha.tripcalculator.Entities.Person;
import com.example.fuinha.tripcalculator.Entities.Transaction;

import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

/**
 * Created by Fuinha on 20/02/2018.
 */

public class PersonTest {
    private Person person;

    @Test
    public void performTests(){
        valuesTest();
    }

    private void populate(){
        person = new Person(69L, "test_user");
        ArrayList<Kitty> kitties = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Kitty kitty = new Kitty(Long.valueOf(i), "kitty" + i);
            kitty.getPeople().add(person);
            for (int j = 0; j < 4; j++) {
                Person user = new Person(Long.valueOf(j+i), "user" + j + i);
                kitty.getPeople().add(user);
                Transaction transaction = new Transaction(user, kitty, new BigDecimal(25));
                kitty.getTransactions().add(transaction);
            }
            kitties.add(kitty);
        }
        person.getKitties().addAll(kitties);
    }

    @Test
    public void valuesTest(){
        populate();
        Transaction transaction = new Transaction(person, person.getKitties().get(0), new BigDecimal(25));
        person.getKitties().get(0).getTransactions().add(transaction);
        assertEquals(new BigDecimal(25), person.getPayedTotal());
        assertEquals(new BigDecimal(105), person.getDebtTotal());
        assertEquals(new BigDecimal(-80), person.getAmountDue());
    }
}
