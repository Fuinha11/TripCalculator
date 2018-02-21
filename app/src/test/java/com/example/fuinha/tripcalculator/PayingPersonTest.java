package com.example.fuinha.tripcalculator;

import com.example.fuinha.tripcalculator.Entities.Kitty;
import com.example.fuinha.tripcalculator.Entities.PayingPerson;
import com.example.fuinha.tripcalculator.Entities.Payment;
import com.example.fuinha.tripcalculator.Entities.Person;
import com.example.fuinha.tripcalculator.Entities.Transaction;

import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

/**
 * Created by Fuinha on 21/02/2018.
 */

public class PayingPersonTest {
    private PayingPerson payer;
    private PayingPerson receiver;

    @Test
    public void performTests(){
        generateValuesTest();
        paymentTest();
    }

    private void populate(){
        Person person = new Person(69L, "test_user");
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
        Transaction transaction = new Transaction(person, person.getKitties().get(0), new BigDecimal(25));
        person.getKitties().get(0).getTransactions().add(transaction);
        // Here the person has 5 kitties
        // Have payed 25
        // Must pay 105
        payer = new PayingPerson(person);

        person = new Person(70L, "test_user");
        kitties = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Kitty kitty = new Kitty(Long.valueOf(i), "kitty" + i);
            kitty.getPeople().add(person);
            for (int j = 0; j < 4; j++) {
                Person user = new Person(Long.valueOf(j+i), "user" + j + i);
                kitty.getPeople().add(user);
                transaction = new Transaction(user, kitty, new BigDecimal(25));
                kitty.getTransactions().add(transaction);
            }
            kitties.add(kitty);
        }
        person.getKitties().addAll(kitties);
        transaction = new Transaction(person, person.getKitties().get(0), new BigDecimal(300));
        person.getKitties().get(0).getTransactions().add(transaction);
        // Here the person has 5 kitties
        // Have payed 300
        // Must pay 160
        receiver = new PayingPerson(person);
    }

    @Test
    public void generateValuesTest(){
        populate();
        assertEquals(new BigDecimal(0), payer.hasToReceive());
        assertEquals(new BigDecimal(80), payer.hasToPay());

        assertEquals(new BigDecimal(140), receiver.hasToReceive());
        assertEquals(new BigDecimal(0), receiver.hasToPay());
    }

    @Test
    public void paymentTest() {
        populate();
        Person other = new Person(0L, "other_person");

        payer.pay(other, new BigDecimal(30.50));
        assertEquals(new BigDecimal(0), payer.hasToReceive());
        assertEquals(new BigDecimal(49.50), payer.hasToPay());

        receiver.receive(other, new BigDecimal(40));
        assertEquals(new BigDecimal(100), receiver.hasToReceive());
        assertEquals(new BigDecimal(0), receiver.hasToPay());
    }
}
