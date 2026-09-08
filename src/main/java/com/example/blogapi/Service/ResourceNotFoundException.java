package com.example.blogapi.Service;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }

    public static ResourceNotFoundException blog(Long id) {
        return new ResourceNotFoundException("Bloggpost med id " + id + " hittades inte");
    }
}
