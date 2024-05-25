package com.hauntedplace.HauntedPlaceAPI.Security.infra;

public class NotFoundException extends RuntimeException {
    public NotFoundException() {}
    public NotFoundException(String message) {
        super(message);
    }
}
