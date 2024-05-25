package com.hauntedplace.HauntedPlaceAPI.Entitys;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.checkerframework.common.aliasing.qual.Unique;

@Entity(name = "users_followers")
@Table(name = "users_followers", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"user_followed_id", "user_follower_id"})
})
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
