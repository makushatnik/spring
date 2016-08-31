package com.cdesign.spittr.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by RealXaker on 31.08.2016.
 */
@ResponseStatus(value = HttpStatus.NOT_MODIFIED,
        reason = "Requested data not modified since")
public class NotModifiedException extends RuntimeException {
    public NotModifiedException(String msg) {
        super(msg);
    }
}
