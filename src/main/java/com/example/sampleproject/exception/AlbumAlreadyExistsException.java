package com.example.sampleproject.exception;

//@ResponseStatus(value = HttpStatus.CONFLICT, reason = "Album already exists!")
public class AlbumAlreadyExistsException extends RuntimeException {

    public AlbumAlreadyExistsException(String message) {
        super(message);
    }
}
