package com.roddy.parking.springboot.parking.exceptions;

public class ParkingSpotNotAvailableException extends RuntimeException {
    public ParkingSpotNotAvailableException(String message) {
        super(message);
    }
}
