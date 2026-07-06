package com.roddy.parking.springboot.parking.advice;

import com.roddy.parking.springboot.parking.exceptions.ParkingSpotNotAvailableException;
import com.roddy.parking.springboot.parking.exceptions.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class HandlerExceptionController {

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, Object> resourceNotFound(ResourceNotFoundException ex) {
        Map<String, Object> error = new HashMap<>();

        error.put("date", new Date());
        error.put("message", ex.getMessage());
        error.put("status", HttpStatus.NOT_FOUND.value());

        return error;
    }

    @ExceptionHandler(ParkingSpotNotAvailableException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public Map<String, Object> spotNotAvailable(ParkingSpotNotAvailableException ex){
        Map<String, Object> error = new HashMap<>();

        error.put("date", new Date());
        error.put("message", ex.getMessage());
        error.put("status", HttpStatus.CONFLICT.value());

        return error;
    }

}

