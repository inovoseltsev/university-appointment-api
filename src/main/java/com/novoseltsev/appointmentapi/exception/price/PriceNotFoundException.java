package com.novoseltsev.appointmentapi.exception.price;

public class PriceNotFoundException extends RuntimeException {

    public PriceNotFoundException() {
    }

    public PriceNotFoundException(String message) {
        super(message);
    }
}
