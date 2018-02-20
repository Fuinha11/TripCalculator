package com.example.fuinha.tripcalculator.Entities;

import com.example.fuinha.tripcalculator.Entities.DataEntities.*;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Fuinha on 20/02/2018.
 */

public class Trip {
    private Long tripId;
    private String tripName;
    private ArrayList<DataKitty> kitties;
    private ArrayList<DataPerson> people;
    private ArrayList<DataTransaction> transactions;
    private ArrayList<PeopleInKitties> peopleInKitties;

    private Long nextId;

    public Long getNextId() {
        return nextId++;
    }

    public Trip(String tripName) {
        this.tripName = tripName;
        this.tripId = 42L;
        this.kitties = new ArrayList<>();
        this.people = new ArrayList<>();
        this.transactions = new ArrayList<>();
        this.peopleInKitties = new ArrayList<>();
        this.nextId = 0L;
    }

    /**
     * Utility methods
     * */

    public Person getSomeone() {
        int index = new Random().nextInt(people.size() - 1);
        return getCompletePerson(people.get(index).getPersonId());
    }




    /**
     * Person CRUD
     * */

    private DataPerson toDataEntity(Person person){
        return new DataPerson(person.getPersonId(), person.getName());
    }

    private DataPerson getDataPerson(Long personId){
        try {
            return people.get(people.indexOf(new DataPerson(personId)));
        } catch (Exception e) {
            return null;
        }
    }

    private Person getCrudePerson(Long personId){
        try {
            DataPerson data = getDataPerson(personId);
            Person result = new Person(data.getPersonId(), data.getName());
            return result;
        } catch (Exception e) {
            return null;
        }
    }

    public Person getCompletePerson(Long personId){
        try {
            Person result = getCrudePerson(personId);
            ArrayList<Kitty> resultKitties = new ArrayList<>();
            ArrayList<Transaction> resultTransactions = new ArrayList<>();

            for (DataTransaction transaction : transactions) {
                if (transaction.getPayerId() == result.getPersonId())
                    resultTransactions.add(getCrudeTransaction(transaction.getTransactionId()));
            }
//            result.setTransactions(resultTransactions);

            for (PeopleInKitties link : peopleInKitties) {
                if (link.getPersonId() == result.getPersonId())
                    resultKitties.add(getCompleteKitty(link.getKittyId()));
            }
            result.setKitties(resultKitties);

            return result;
        } catch (Exception e) {
            return null;
        }
    }

    public ArrayList<Person> getEveryone(){
        ArrayList<Person> result = new ArrayList<>();
        for (DataPerson data:people){
            result.add(getCrudePerson(data.getPersonId()));
        }
        return result;
    }

    public Person createPerson(Person person){
        Long id = getNextId();
        people.add(toDataEntity(person.setPersonId(id)));
        if (updatePerson(person))
            return getCompletePerson(id);
        else
            return null;
    }

    public boolean updatePerson(Person person){
        try {
            DataPerson oldPerson = getDataPerson(person.getPersonId());
            oldPerson.setName(person.getName());
            for (Kitty kitty : person.getKitties()) {
                PeopleInKitties link = new PeopleInKitties(person.getPersonId(), kitty.getKittyId());
                if (!peopleInKitties.contains(link))
                    peopleInKitties.add(link);
            }

            ArrayList<PeopleInKitties> modList = new ArrayList<>();
            for (PeopleInKitties link : peopleInKitties) {
                Kitty kitty = new Kitty(link.getKittyId());
                if (link.getPersonId() == person.getPersonId() && !person.getKitties().contains(kitty))
                    modList.add(link);
            }
            peopleInKitties.removeAll(modList);
            return true;
        } catch (Exception e){
            return false;
        }
    }

    public boolean deletePerson(Person person){
        try {
            people.remove(toDataEntity(person));
            ArrayList<PeopleInKitties> modList = new ArrayList<>();
            for (PeopleInKitties link : peopleInKitties){
                if (link.getPersonId() == person.getPersonId())
                    modList.add(link);
            }
            peopleInKitties.removeAll(modList);
            ArrayList<DataTransaction> modTran = new ArrayList<>();
            for (DataTransaction transaction : transactions){
                if (transaction.getPayerId() == person.getPersonId())
                    modTran.add(transaction);
            }
            transactions.removeAll(modTran);
            return true;
        } catch (Exception e){
            return false;
        }
    }


    /**
     * Kitty CRUD
     * */

    private DataKitty toDataEntity(Kitty kitty){
        return new DataKitty(kitty.getKittyId(), kitty.getName());
    }

    private DataKitty getDataKitty(Long kittyId){
        try {
            return kitties.get(kitties.indexOf(new DataKitty(kittyId)));
        } catch (Exception e) {
            return null;
        }
    }

    private Kitty getCrudeKitty(Long kittyId){
        try {
            DataKitty data = getDataKitty(kittyId);
            return new Kitty(kittyId, data.getName());
        } catch (Exception e) {
            return null;
        }
    }

    public Kitty getCompleteKitty(Long kittyId){
        try {
            Kitty result = getCrudeKitty(kittyId);
            ArrayList<Person> resultPeople = new ArrayList<>();
            ArrayList<Transaction> resultTransactions = new ArrayList<>();

            for (DataTransaction transaction : transactions) {
                if (transaction.getKittyId() == result.getKittyId())
                    resultTransactions.add(getCrudeTransaction(transaction.getTransactionId()));
            }
            result.setTransactions(resultTransactions);

            for (PeopleInKitties link : peopleInKitties) {
                if (link.getKittyId() == result.getKittyId())
                    resultPeople.add(getCrudePerson(link.getPersonId()));
            }
            result.setPeople(resultPeople);

            return result;
        } catch (Exception e) {
            return null;
        }
    }

    public ArrayList<Kitty> getAllKitties(){
        ArrayList<Kitty> result = new ArrayList<>();
        for (DataKitty data:kitties){
            result.add(getCrudeKitty(data.getKittyId()));
        }
        return result;
    }

    public Kitty createKitty(Kitty kitty){
        Long id = getNextId();
        kitties.add(toDataEntity(kitty.setKittyId(id)));
        if (updateKitty(kitty))
            return getCompleteKitty(id);
        else
            return null;
    }

    public boolean updateKitty(Kitty kitty){
        try {
            DataKitty oldKitty = getDataKitty(kitty.getKittyId());
            oldKitty.setName(kitty.getName());
            ArrayList<PeopleInKitties> modList = new ArrayList<>();
            for (Person person : kitty.getPeople()) {
                PeopleInKitties link = new PeopleInKitties(person.getPersonId(), kitty.getKittyId());
                if (!peopleInKitties.contains(link))
                    modList.add(link);
            }
            peopleInKitties.addAll(modList);
            modList.clear();
            for (PeopleInKitties link : peopleInKitties) {
                Person person = new Person(link.getPersonId(), null);
                if (link.getKittyId() == kitty.getKittyId() && !kitty.getPeople().contains(person))
                    modList.add(link);
            }
            peopleInKitties.removeAll(modList);
            return true;
        } catch (Exception e){
            return false;
        }
    }

    public boolean deleteKitty(Kitty kitty){
        try {
            kitties.remove(toDataEntity(kitty));
            ArrayList<PeopleInKitties> modList = new ArrayList<>();
            for (PeopleInKitties link : peopleInKitties){
                if (link.getKittyId() == kitty.getKittyId())
                    modList.add(link);
            }
            peopleInKitties.removeAll(modList);
            ArrayList<DataTransaction> modTran = new ArrayList<>();
            for (DataTransaction transaction : transactions){
                if (transaction.getKittyId() == kitty.getKittyId())
                    modTran.add(transaction);
            }
            transactions.removeAll(modTran);
            return true;
        } catch (Exception e){
            return false;
        }
    }


    /**
     * Transaction CRUD
     * */

    private DataTransaction toDataEntity(Transaction transaction){
        return new DataTransaction(transaction.getTransactionId(),
                transaction.getPayer().getPersonId(),
                transaction.getKitty().getKittyId(),
                transaction.getValue());
    }

    private DataTransaction getDataTransaction(Long transactionId) {
        try {
            return transactions.get(transactions.indexOf(new DataTransaction(transactionId)));
        } catch (Exception e){
            return null;
        }
    }

    public Transaction getCrudeTransaction(Long transactionId){
        try {
            DataTransaction data = getDataTransaction(transactionId);
            DataPerson dataPerson = getDataPerson(data.getPayerId());
            Person person = new Person(dataPerson.getPersonId(), dataPerson.getName());
            DataKitty dataKitty = getDataKitty(data.getKittyId());
            Kitty kitty = new Kitty(dataKitty.getKittyId(), dataKitty.getName());
            return new Transaction(transactionId, person, kitty, data.getValue());
        } catch (Exception e) {
            return null;
        }
    }

    public ArrayList<Transaction> getAllTransactions(){
        ArrayList<Transaction> resultTransactions = new ArrayList<>();

        for (DataTransaction data : transactions){
            DataPerson dataPerson = getDataPerson(data.getPayerId());
            Person person = new Person(dataPerson.getPersonId(), dataPerson.getName());
            DataKitty dataKitty = getDataKitty(data.getKittyId());
            Kitty kitty = new Kitty(dataKitty.getKittyId(), dataKitty.getName());
            Transaction temp = new Transaction(data.getTransactionId(), person, kitty, data.getValue());
            resultTransactions.add(temp);
        }

        return resultTransactions;
    }

    public Transaction createTransaction(Transaction transaction){
        Long id = getNextId();
        transactions.add(toDataEntity(transaction.setTransactionId(id)));
        if (updateTransaction(transaction))
            return getCrudeTransaction(id);
        else
            return null;
    }

    public boolean updateTransaction(Transaction transaction) {
        try {
            DataTransaction oldTransaction = getDataTransaction(transaction.getTransactionId());
            oldTransaction.setPayerId(transaction.getPayer().getPersonId())
                    .setKittyId(transaction.getKitty().getKittyId())
                    .setValue(transaction.getValue());
            return true;
        } catch (Exception e){
            return false;
        }
    }

    public boolean deleteTransaction(Transaction transaction){
        try {
            transactions.remove(toDataEntity(transaction));
            return true;
        } catch (Exception e){
            return false;
        }
    }
}
