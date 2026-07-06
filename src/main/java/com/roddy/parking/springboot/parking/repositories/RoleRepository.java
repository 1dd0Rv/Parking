package com.roddy.parking.springboot.parking.repositories;

import com.roddy.parking.springboot.parking.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
