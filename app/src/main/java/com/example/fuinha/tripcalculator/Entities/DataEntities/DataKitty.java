package com.example.fuinha.tripcalculator.Entities.DataEntities;

/**
 * Created by Fuinha on 20/02/2018.
 */

public class DataKitty {
    private Long kittyId;
    private String name;

    public DataKitty(Long kittyId) {
        this(kittyId, null);
    }

    public DataKitty(Long kittyId, String name) {
        this.kittyId = kittyId;
        this.name = name;
    }

    public Long getKittyId() {
        return kittyId;
    }

    public DataKitty setKittyId(Long kittyId) {
        this.kittyId = kittyId;
        return this;
    }

    public String getName() {
        return name;
    }

    public DataKitty setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof DataKitty && ((DataKitty) obj).kittyId == this.kittyId;
    }
}
