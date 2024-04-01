package com.example.desarrollodeaplicaciones.exceptions;

public class ImageNotFoundException extends RuntimeException {
    public ImageNotFoundException(String mediaId) {
        super("Image with ID " + mediaId + " not found");
    }
}
