package com.roddy.parking.springboot.parking.security;

import com.fasterxml.jackson.annotation.JsonCreator;   // <- Jackson 3 does NOT change this package
import com.fasterxml.jackson.annotation.JsonProperty;   // <- same

public abstract class SimpleGrantedAuthorityJsonCreator {

    @JsonCreator
    public SimpleGrantedAuthorityJsonCreator(@JsonProperty("authority") String role) {
    }
}

