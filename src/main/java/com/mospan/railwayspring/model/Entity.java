package com.mospan.railwayspring.model;

import java.io.Serializable;

public abstract class Entity implements Serializable {

    private long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
