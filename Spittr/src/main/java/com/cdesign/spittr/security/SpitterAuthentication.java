package com.cdesign.spittr.security;

import com.cdesign.spittr.data.entity.Spitter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

/**
 * Created by Ageev Evgeny on 29.08.2016.
 */
public class SpitterAuthentication implements Authentication {

    private final Spitter spitter;
    private boolean authenticated = true;

    public SpitterAuthentication(Spitter spitter) {
        this.spitter = spitter;
    }

    @Override
    public String getName() {
        return spitter.getUsername();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return spitter.getAuthorities();
    }

    @Override
    public Object getCredentials() {
        return spitter.getPassword();
    }

    @Override
    public Spitter getDetails() {
        return spitter;
    }

    @Override
    public Object getPrincipal() {
        return spitter.getUsername();
    }

    @Override
    public boolean isAuthenticated() {
        return authenticated;
    }

    @Override
    public void setAuthenticated(boolean authenticated) {
        this.authenticated = authenticated;
    }
}