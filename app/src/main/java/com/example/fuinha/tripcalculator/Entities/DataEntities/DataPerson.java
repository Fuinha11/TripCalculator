package com.example.fuinha.tripcalculator.Entities.DataEntities;

/**
 * Created by Fuinha on 20/02/2018.
 */

public class DataPerson {
    private Long personId;
    private String name;

    public DataPerson(Long personId) {
        this(personId, null);
    }

    public DataPerson(Long personId, String name) {
        this.personId = personId;
        this.name = name;
    }

    public Long getPersonId() {
        return personId;
    }

    public DataPerson setPersonId(Long personId) {
        this.personId = personId;
        return this;
    }

    public String getName() {
        return name;
    }

    public DataPerson setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof DataPerson && ((DataPerson) obj).personId == this.personId;
    }
}
