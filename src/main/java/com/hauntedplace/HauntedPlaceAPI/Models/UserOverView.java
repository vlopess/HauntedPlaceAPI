package com.hauntedplace.HauntedPlaceAPI.Models;

import com.hauntedplace.HauntedPlaceAPI.Entitys.User;


public record UserOverView(String encryptedId, String username, String bio, String profileImageUrl) {
    public UserOverView(User user){
        this(new EncryptedId(user.getId()).get(), user.getUsername(), user.getBio(), user.getProfilePictureUrl());
    }
}
