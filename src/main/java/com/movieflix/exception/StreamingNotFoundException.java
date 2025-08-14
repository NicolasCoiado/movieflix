package com.movieflix.exception;

public class StreamingNotFoundException extends RuntimeException {
    public StreamingNotFoundException(String message) {
        super(message);
    }
}
