package com.cdesign.spittr.web.response;

import com.cdesign.spittr.data.entity.Spitter;

/**
 * Created by RealXaker on 29.08.2016.
 */
public class LoginResponse {
    public String token;
    public Spitter spitter;

    public LoginResponse(String token, Spitter spitter) {
        this.token = token;
        this.spitter = spitter;
    }
}
