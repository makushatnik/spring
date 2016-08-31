package com.cdesign.spittr.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by RealXaker on 29.08.2016.
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND,
    reason = "Requested data not found")
public class NotFoundException extends RuntimeException {
    public NotFoundException(String msg) {
        super(msg);
    }
}
