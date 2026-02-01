package com.authentication.service;

import com.authentication.entity.User;
import com.authentication.entity.UserPrincipal;
import com.authentication.repository.AuthRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private AuthRepository repo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> user = repo.findByEmail(email);
        if(user.isEmpty()) {
            System.out.println("401 not found");
            throw new UsernameNotFoundException("User not found");
        }
        return new UserPrincipal(user.get());

    }

}
