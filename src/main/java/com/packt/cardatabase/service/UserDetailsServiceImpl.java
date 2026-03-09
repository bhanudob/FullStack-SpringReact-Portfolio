package com.packt.cardatabase.service;

import com.packt.cardatabase.domain.AppUser;
import com.packt.cardatabase.domain.AppUserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final AppUserRepository repository;

    public UserDetailsServiceImpl(AppUserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        Optional<AppUser> user = repository.findByUsername(username);

        if (user.isPresent()) {
            AppUser currentUser = user.get();
            return org.springframework.security.core.userdetails.User
                    .withUsername(currentUser.getUsername())
                    .password(currentUser.getPassword())
                    .roles(currentUser.getRole())
                    .build();
        }
        throw new UsernameNotFoundException("User not found: " + username);
    }
}
