package com.hauntedplace.HauntedPlaceAPI.Security.infra;

public class BadRequestException extends RuntimeException {
    public BadRequestException() {}
    public BadRequestException(String message) {
        super(message);
    }
}
