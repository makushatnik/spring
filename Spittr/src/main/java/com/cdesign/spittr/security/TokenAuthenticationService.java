package com.cdesign.spittr.security;

import com.cdesign.spittr.data.entity.Spitter;
import com.cdesign.spittr.data.service.SpitterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * Created by Ageev Evgeny on 29.08.2016.
 */
@Service
public class TokenAuthenticationService {

    private static final String AUTH_HEADER_NAME = "X-AUTH-TOKEN";
    private static final long SIX_MONTHS = 1000L * 60 * 60 * 24 * 30 * 6;

    private final TokenHandler tokenHandler;

    @Autowired
    public TokenAuthenticationService(@Value("${token.secret}") String secret,
                                      SpitterService spitterService) {
        tokenHandler = new TokenHandler(secret, spitterService);
    }

    public String addAuthentication(HttpServletResponse response, Spitter spitter) {
        Date expiration = new Date(System.currentTimeMillis() + SIX_MONTHS);
        String token = tokenHandler.createTokenForUser(spitter, expiration);
        response.addHeader(AUTH_HEADER_NAME, token);
        return token;
    }

    public Authentication getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(AUTH_HEADER_NAME);
        if (token == null) {
            token = request.getParameter("token");
        }
        if (token != null) {
            final Spitter spitter = tokenHandler.parseSpitterFromToken(token);
            if (spitter != null) {
                return new SpitterAuthentication(spitter);
            }
        }
        return null;
    }

    public String createToken(Spitter spitter, long availabilityPeriod) {
        Date expiration = new Date(System.currentTimeMillis() + availabilityPeriod);
        return tokenHandler.createTokenForUser(spitter, expiration);
    }
}
