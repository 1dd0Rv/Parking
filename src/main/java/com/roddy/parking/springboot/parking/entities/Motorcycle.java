package com.roddy.parking.springboot.parking.entities;

import jakarta.persistence.Entity;

@Entity
public class Motorcycle extends Vehicle{

    private Boolean sidecar;

    public Boolean getSidecar() {
        return sidecar;
    }

    public void setSidecar(Boolean sidecar) {
        this.sidecar = sidecar;
    }
}
