package com.cdesign.spittr.security;

import com.cdesign.spittr.data.entity.Spitter;
import com.cdesign.spittr.data.repo.SpitterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Created by Ageev Evgeny on 30.08.2016.
 */
@Service
public class SpitterDetailsService implements UserDetailsService {
    @Autowired
    private SpitterRepository spitterRepository;

    @Override
    public final Spitter loadUserByUsername(String username) throws UsernameNotFoundException {
        Spitter user = spitterRepository.findByUsername(username);
        if (user == null) {
            throw new BadCredentialsException("Invalid email or username");
        }
        return user;
    }
}
