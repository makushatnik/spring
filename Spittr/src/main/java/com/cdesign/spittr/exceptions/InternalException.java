package com.cdesign.spittr.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by RealXaker on 29.08.2016.
 */
@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class InternalException extends Exception {
    public InternalException(String msg) {
        super(msg);
    }
}
