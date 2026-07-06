package com.roddy.parking.springboot.parking.entities;

import jakarta.persistence.Entity;

@Entity
public class Car extends Vehicle {

    private Integer doors;


    public Integer getDoors() {
        return doors;
    }

    public void setDoors(Integer doors) {
        this.doors = doors;
    }
}
