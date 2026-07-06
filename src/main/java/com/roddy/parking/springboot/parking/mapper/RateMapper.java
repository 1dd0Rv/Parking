package com.roddy.parking.springboot.parking.mapper;

import com.roddy.parking.springboot.parking.DTO.RateDTO;
import com.roddy.parking.springboot.parking.entities.Rate;
import org.springframework.stereotype.Component;

@Component
public class RateMapper {

    public Rate toEntity(RateDTO dto){
        Rate rate = new Rate();
        rate.setId(dto.getId());
        rate.setType(dto.getType());
        rate.setPricePerDay(dto.getPricePerDay());
        rate.setPricePerHour(dto.getPricePerHour());
        rate.setPricePerMinute(dto.getPricePerMinute());

        return rate;
    }

    public RateDTO toDto(Rate rate){
        RateDTO dto = new RateDTO();
        dto.setId(rate.getId());
        dto.setType(rate.getType());
        dto.setPricePerDay(rate.getPricePerDay());
        dto.setPricePerHour(rate.getPricePerHour());
        dto.setPricePerMinute(rate.getPricePerMinute());

        return dto;
    }
}
