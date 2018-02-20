package com.example.fuinha.tripcalculator;

import com.example.fuinha.tripcalculator.Entities.Kitty;
import com.example.fuinha.tripcalculator.Entities.Person;
import com.example.fuinha.tripcalculator.Entities.Transaction;
import com.example.fuinha.tripcalculator.Entities.Trip;

import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

/**
 * Created by Fuinha on 20/02/2018.
 */

public class TripTest {
    private Trip trip;

    private void populate(){
        trip = new Trip("test_trip");

        for (int i = 0; i < 3; i++) {
            Person user = new Person("user" + i);
            trip.createPerson(user);
            Kitty kitty = new Kitty(0L, "kitty" + i);
            kitty.getPeople().add(user);
            trip.createKitty(kitty);
            Transaction transaction = new Transaction(user, kitty, new BigDecimal(60 + i));
            trip.createTransaction(transaction);
            trip.createTransaction(transaction);
            trip.createTransaction(transaction);
        }
    }

    @Test
    public void getNextIdTest() throws Exception {
        Trip trip = new Trip("test");
        Long zero = trip.getNextId();
        Long one = trip.getNextId();
        assertEquals(0L, zero, 0.1);
        assertEquals(1L, one, 0.1);
    }

    @Test
    public void crudPerson(){
        populate();
        Person user = new Person("user");
        user = trip.createPerson(user);
        Person newUser = trip.getCompletePerson(user.getPersonId());
        assertEquals(user, newUser);
        assertEquals(user.getName(), newUser.getName());

        user = trip.getEveryone().get(1);
        user = trip.getCompletePerson(user.getPersonId());
        user.setName("new name");
        trip.updatePerson(user);
        newUser = trip.getCompletePerson(user.getPersonId());
        assertEquals(user.getName(), newUser.getName());

        trip.deletePerson(user);
        newUser = trip.getCompletePerson(user.getPersonId());
        assertEquals(null, newUser);
    }

    @Test
    public void crudKitty(){
        populate();
        Kitty kitty = new Kitty("Test");
        kitty.setPeople(trip.getEveryone());
        kitty = trip.createKitty(kitty);
        Kitty newKitty = trip.getCompleteKitty(kitty.getKittyId());
        assertEquals(kitty, newKitty);
        assertEquals(kitty.getName(), newKitty.getName());
        assertEquals(kitty.getPeople(), newKitty.getPeople());

        kitty.setName("new name");
        kitty.getPeople().clear();
        trip.updateKitty(kitty);
        newKitty = trip.getCompleteKitty(kitty.getKittyId());
        assertEquals(kitty, newKitty);
        assertEquals(kitty.getName(), newKitty.getName());
        assertEquals(kitty.getPeople(), newKitty.getPeople());

        kitty.getPeople().add(trip.getEveryone().get(0));
        trip.updateKitty(kitty);
        newKitty = trip.getCompleteKitty(kitty.getKittyId());
        assertEquals(kitty, newKitty);
        assertEquals(kitty.getName(), newKitty.getName());
        assertEquals(kitty.getPeople(), newKitty.getPeople());

        trip.deleteKitty(kitty);
        newKitty = trip.getCompleteKitty(kitty.getKittyId());
        assertEquals(null, newKitty);
    }

    @Test
    public void crudTransaction(){
        populate();
        Person person = trip.getSomeone();
        Kitty kitty = trip.getAllKitties().get(0);
        Transaction transaction = new Transaction(person, kitty, new BigDecimal(69));
        transaction = trip.createTransaction(transaction);
        Transaction newTransaction = trip.getCrudeTransaction(transaction.getTransactionId());
        assertEquals(transaction, newTransaction);
        assertEquals(transaction.getPayer(), newTransaction.getPayer());
        assertEquals(transaction.getKitty(), newTransaction.getKitty());
        assertEquals(transaction.getValue(), newTransaction.getValue());

        person = trip.getSomeone();
        kitty = trip.getAllKitties().get(1);
        transaction.setPayer(person)
                .setKitty(kitty)
                .setValue(new BigDecimal(42));
        trip.updateTransaction(transaction);
        newTransaction = trip.getCrudeTransaction(transaction.getTransactionId());
        assertEquals(transaction, newTransaction);
        assertEquals(transaction.getPayer(), newTransaction.getPayer());
        assertEquals(transaction.getKitty(), newTransaction.getKitty());
        assertEquals(transaction.getValue(), newTransaction.getValue());

        trip.deleteTransaction(transaction);
        newTransaction = trip.getCrudeTransaction(transaction.getTransactionId());
        assertEquals(null, newTransaction);
    }
}
