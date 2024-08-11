package com.example.sampleproject.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "Album already exists!")
public class AlbumAlreadyExistsException extends RuntimeException {

    public AlbumAlreadyExistsException(String message) {
        super(message);
    }
}
