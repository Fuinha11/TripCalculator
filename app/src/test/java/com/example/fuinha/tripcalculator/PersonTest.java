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
        getTransactionsTest();
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
        assertEquals(new BigDecimal(80), person.getAmountDue());
    }

    @Test
    public void getTransactionsTest(){
        populate();
        ArrayList<Transaction> transactions = person.getTransactions();
        //Person has no Transactions so far
        assertEquals(0, transactions.size());

        Kitty kitty = person.getKitties().get(0);
        Transaction transaction = new Transaction(0L, person, kitty, new BigDecimal(50));
        kitty.getTransactions().add(transaction);
        transactions.add(transaction);
        transaction = new Transaction(1L, person, kitty, new BigDecimal(50));
        kitty.getTransactions().add(transaction);
        transactions.add(transaction);
        transaction = new Transaction(2L, person, kitty, new BigDecimal(50));
        kitty.getTransactions().add(transaction);
        transactions.add(transaction);
        //Person has now 3 transactions

        assertEquals(transactions, person.getTransactions());

        kitty = person.getKitties().get(1);
        transaction = new Transaction(0L, person, kitty, new BigDecimal(50));
        kitty.getTransactions().add(transaction);
        transactions.add(transaction);
        transaction = new Transaction(1L, person, kitty, new BigDecimal(50));
        kitty.getTransactions().add(transaction);
        transactions.add(transaction);
        transaction = new Transaction(2L, person, kitty, new BigDecimal(50));
        kitty.getTransactions().add(transaction);
        transactions.add(transaction);
        //Person has now 6 transactions in 2 kitties

        assertEquals(transactions, person.getTransactions());
    }
}
