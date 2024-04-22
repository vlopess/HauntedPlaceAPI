package com.hauntedplace.HauntedPlaceAPI.Entitys;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;

@Entity(name = "users_followers")
public class UserFollower {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long user_followed_id;
    @NotNull
    private Long user_follower_id;

    public UserFollower(){}

    public UserFollower(Long user_followed_id, Long user_follower_id) {
        this.user_followed_id = user_followed_id;
        this.user_follower_id = user_follower_id;
    }
}
