package com.example.desarrollodeaplicaciones.exceptions;

public class ImageDeleteException extends RuntimeException {
    public ImageDeleteException() {
        super("Error deleting image");
    }
}
