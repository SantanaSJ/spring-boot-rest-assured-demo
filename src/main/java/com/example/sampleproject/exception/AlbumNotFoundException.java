package com.example.sampleproject.exception;

//@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Resource not found!")
public class AlbumNotFoundException extends RuntimeException {

    public AlbumNotFoundException(String message) {
        super(message);
    }
}

