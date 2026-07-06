package com.roddy.parking.springboot.parking.services;

import com.roddy.parking.springboot.parking.entities.Administrator;
import com.roddy.parking.springboot.parking.repositories.AdministratorRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class JpaUserDetailService implements UserDetailsService {

    private final AdministratorRepository repository;

    public JpaUserDetailService(AdministratorRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Administrator> optionalAdmin = repository.findByUsername(username);
        if (optionalAdmin.isEmpty()) {
            throw new UsernameNotFoundException("Usuario " + username + " no existe");
        }
        Administrator admin = optionalAdmin.orElseThrow();

        List<GrantedAuthority> authorities = admin.getRoles().stream().map(role ->
                        new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());

        return new org.springframework.security.core.userdetails.User(admin.getUsername(),
                admin.getPassword(),
                admin.getEnabled(),
                true,
                true,
                true,
                authorities);
    }
}

