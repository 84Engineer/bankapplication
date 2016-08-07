package com.luxoft.bankapp.model;

public class FeedException extends RuntimeException {

    public FeedException(String message) {
        super(message);
    }
}