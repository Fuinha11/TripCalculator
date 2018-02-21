package com.example.fuinha.tripcalculator;

import com.example.fuinha.tripcalculator.Entities.Kitty;
import com.example.fuinha.tripcalculator.Entities.PayingPerson;
import com.example.fuinha.tripcalculator.Entities.Person;
import com.example.fuinha.tripcalculator.Entities.Transaction;
import com.example.fuinha.tripcalculator.Entities.Trip;

import org.junit.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Random;

import static org.junit.Assert.assertEquals;

/**
 * Created by Fuinha on 20/02/2018.
 */

public class TripTest {
    private Trip trip;

    @Test
    public void performTests(){
        getNextIdTest();
        crudPerson();
        crudKitty();
        crudTransaction();
        getAllTransactionsTest();
        deleteTransactionsFromPerson();
        deleteTransactionsFromKitty();
        valuesTest();
        getPaymentsTest();
    }

    private void populate(){
        trip = new Trip("test_trip");

        for (int i = 0; i < (new Random().nextInt(100) + 1); i++) {
            Person user = new Person("user" + i);
            trip.createPerson(user);

            Kitty kitty = new Kitty(0L, "kitty" + i);
            kitty.getPeople().add(user);
            for (int j = 0; j < new Random().nextInt(45); j++) {
                Person otherUser = trip.getSomeone();
                if (!kitty.getPeople().contains(otherUser))
                    kitty.getPeople().add(otherUser);
            }
            trip.createKitty(kitty);

            for (int j = 0; j < new Random().nextInt(42); j++) {
                Transaction transaction = new Transaction(kitty.getSomeone(), kitty, new BigDecimal(60 + j));
                trip.createTransaction(transaction);
            }
        }
    }

    @Test
    public void getNextIdTest(){
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

    @Test
    public void getAllTransactionsTest(){
        populate();
        ArrayList<Person> everyone = new ArrayList<>();
        for (Person p : trip.getEveryone()){
            everyone.add(trip.getCompletePerson(p.getPersonId()));
        }

        ArrayList<Transaction> transactions = new ArrayList<>();
        for (Person p : everyone){
            transactions.addAll(p.getTransactions());
        }

        ArrayList<Transaction> tripTransactions = trip.getAllTransactions();

        assertEquals(transactions.size(), tripTransactions.size());
    }

    @Test
    public void deleteTransactionsFromPerson(){
        populate();
        Person dude = trip.getCompletePerson(0L);
        // Dude have 1 kitty with 3 transactions in it;
        ArrayList<Transaction> dudeTransactions = dude.getTransactions();
        trip.deletePerson(dude);

        for (Transaction t : dudeTransactions){
            assertEquals(false, trip.getAllTransactions().contains(t));
        }
    }

    @Test
    public void deleteTransactionsFromKitty(){
        populate();
        Kitty kitty = trip.getAllKitties().get(0);
        // Kitty have 3 transactions in it;
        ArrayList<Transaction> kittyTransactions = kitty.getTransactions();
        trip.deleteKitty(kitty);

        for (Transaction t : kittyTransactions){
            assertEquals(false, trip.getAllTransactions().contains(t));
        }
    }

    @Test
    public void valuesTest(){
        populate();
        BigDecimal allPayments = new BigDecimal(0);
        allPayments = allPayments.setScale(2, RoundingMode.DOWN);

        for (Transaction t : trip.getAllTransactions()){
            allPayments = allPayments.add(t.getValue());
        }

        BigDecimal allKitties = new BigDecimal(0);
        allKitties = allKitties.setScale(2, RoundingMode.DOWN);
        BigDecimal allKittiesPP = new BigDecimal(0);
        allKittiesPP = allKittiesPP.setScale(2, RoundingMode.DOWN);
        for (Kitty k : trip.getAllKitties()){
            allKitties = allKitties.add(k.getTotal());
            allKittiesPP = allKittiesPP.add(k.getTotalPerPerson().multiply(new BigDecimal(k.getNumOfPeople())));
        }

        BigDecimal allPeopleDebt = new BigDecimal(0);
        allPeopleDebt = allPeopleDebt.setScale(2, RoundingMode.DOWN);
        BigDecimal allPeoplePayed = new BigDecimal(0);
        allPeoplePayed = allPeoplePayed.setScale(2, RoundingMode.DOWN);
        BigDecimal allPeopleDue = new BigDecimal(0);
        allPeopleDue = allPeopleDue.setScale(2, RoundingMode.DOWN);
        for (Person p : trip.getEveryone()){
            p = trip.getCompletePerson(p.getPersonId());
            allPeopleDebt = allPeopleDebt.add(p.getDebtTotal());
            allPeoplePayed = allPeoplePayed.add(p.getPaidTotal());
            allPeopleDue = allPeopleDue.add(p.getAmountDue());
        }


        assertEquals(allKitties.floatValue(), allKittiesPP.floatValue(), 1.0f);
        assertEquals(allPeopleDebt.floatValue(), allPeoplePayed.floatValue(), 1.0f);
        assertEquals(allPeoplePayed.floatValue(), allPayments.floatValue(), 1.0f);
        assertEquals(allKitties.floatValue(), allPayments.floatValue(), 1.0f);
        assertEquals(0f, allPeopleDue.floatValue(), 1.0f);
    }

    @Test
    public void getPaymentsTest(){
        populate();
        ArrayList<PayingPerson> people =  trip.getFinalPayments();
        BigDecimal hasToPay = new BigDecimal(0);
        BigDecimal hasToReceive = new BigDecimal(0);
        BigDecimal willPay = new BigDecimal(0);
        BigDecimal willPayAbs = new BigDecimal(0);

        for (PayingPerson p : people){
            hasToPay = hasToPay.add(p.hasToPay());
            hasToReceive = hasToReceive.add(p.hasToReceive());
            willPay = willPay.add(p.willPay());
            willPayAbs = willPayAbs.add(p.willPay().abs());
        }

        BigDecimal allPeopleDueAbs = new BigDecimal(0);
        allPeopleDueAbs = allPeopleDueAbs.setScale(2, RoundingMode.DOWN);

        for (Person p : trip.getEveryone()){
            p = trip.getCompletePerson(p.getPersonId());
            allPeopleDueAbs = allPeopleDueAbs.add(p.getAmountDue().abs());
        }

        assertEquals(new BigDecimal(0).floatValue(), hasToPay.floatValue(), 1.0);
        assertEquals(new BigDecimal(0).floatValue(), hasToReceive.floatValue(), 1.0);
        assertEquals(new BigDecimal(0).floatValue(), willPay.floatValue(), 1.0);
        assertEquals(allPeopleDueAbs.floatValue(), willPayAbs.floatValue(), 1.0);
    }
}
