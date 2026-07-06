package com.roddy.parking.springboot.parking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class ParkingApplication {

    public static void main(String[] args) {
        System.out.println(new BCryptPasswordEncoder().encode("admin1234"));
        SpringApplication.run(ParkingApplication.class, args);
    }



}
