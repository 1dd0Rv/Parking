package com.roddy.parking.springboot.parking.DTO;


public class MotorcycleDTO {

        private String licensePlate;
        private String brand;
        private String model;
        private String color;

        private Boolean sidecar;

    public MotorcycleDTO() {
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Boolean getSidecar() {
        return sidecar;
    }

    public void setSidecar(Boolean sidecar) {
        this.sidecar = sidecar;
    }
}

