package com.cdesign.spittr.security;

import com.cdesign.spittr.data.entity.Spitter;
import com.cdesign.spittr.data.service.SpitterService;

import java.util.Date;

/**
 * import io.jsonwebtoken.Jwts;
 * import io.jsonwebtoken.SignatureAlgorithm;
 */

/**
 * Created by AgeevEvgeny on 29.08.2016.
 */
public final class TokenHandler {

    private final String secret;
    private final SpitterService spitterService;

    public TokenHandler(String secret, SpitterService spitterService) {
        this.secret = secret;
        this.spitterService = spitterService;
    }

    public Spitter parseSpitterFromToken(String token) {
//        String username = Jwts.parser()
//                .setSigningKey(secret)
//                .parseClaimsJws(token)
//                .getBody()
//                .getSubject();
        String username = "";
        return spitterService.findByUserName(username);
    }

    public String createTokenForUser(Spitter spitter, Date expiration) {
//        return Jwts.builder()
//                .setSubject(spitter.getEmail())
//                .setExpiration(expiration)
//                .signWith(SignatureAlgorithm.HS512, secret)
//                .compact();
        return "s3cret";
    }
}