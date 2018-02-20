package com.example.fuinha.tripcalculator.Entities.DataEntities;

/**
 * Created by Fuinha on 20/02/2018.
 */

public class PeopleInKitties {
    private Long personId;
    private Long kittyId;

    public PeopleInKitties(Long personId, Long kittyId) {
        this.personId = personId;
        this.kittyId = kittyId;
    }

    public Long getPersonId() {
        return personId;
    }

    public PeopleInKitties setPersonId(Long personId) {
        this.personId = personId;
        return this;
    }

    public Long getKittyId() {
        return kittyId;
    }

    public PeopleInKitties setKittyId(Long kittyId) {
        this.kittyId = kittyId;
        return this;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof PeopleInKitties
                && ((PeopleInKitties) obj).personId == personId
                && ((PeopleInKitties) obj).kittyId == kittyId;
    }
}
