package com.roddy.parking.springboot.parking.repositories;

import com.roddy.parking.springboot.parking.entities.Administrator;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdministratorRepository extends JpaRepository<Administrator, Long> {

    Optional<Administrator> findByUsername(String username);
}
