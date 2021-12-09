package com.newts.newtapp.api.security;

import com.newts.newtapp.api.gateways.UserRepository;
import com.newts.newtapp.entities.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

public class NewtUserDetailsService implements UserDetailsService {
    final UserRepository repository;

    public NewtUserDetailsService(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = repository.findByUsername(username);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("Username not found.");
        }
        return user.get();
    }
}
