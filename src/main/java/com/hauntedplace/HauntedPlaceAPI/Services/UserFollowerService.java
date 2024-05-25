package com.hauntedplace.HauntedPlaceAPI.Services;

import com.hauntedplace.HauntedPlaceAPI.Entitys.User;
import com.hauntedplace.HauntedPlaceAPI.Entitys.UserFollower;
import com.hauntedplace.HauntedPlaceAPI.Repository.UserFollowerRepository;
import com.hauntedplace.HauntedPlaceAPI.Repository.UserRepository;
import com.hauntedplace.HauntedPlaceAPI.Security.infra.BadRequestException;
import com.hauntedplace.HauntedPlaceAPI.Security.infra.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class UserFollowerService {
    final private UserRepository userRepository;
    final private UserFollowerRepository userFollowerRepository;

    @Autowired
    public UserFollowerService(UserRepository userRepository, UserFollowerRepository userFollower){
        this.userRepository = userRepository;
        this.userFollowerRepository = userFollower;
    }

    public void followerUser(Long user_followed_id, Long user_follower_id){
        if(Objects.equals(user_followed_id, user_follower_id)) throw new BadRequestException();
        Optional<User> followedOp = getUserById(user_followed_id);
        Optional<User> followerOp = getUserById(user_follower_id);
        if(followedOp.isEmpty() || followerOp.isEmpty()) throw new NotFoundException();
        User followed = followedOp.get();
        User follower = followerOp.get();
        follower.getFollowing().add(followed);
        userRepository.save(follower);
    }

    public void unFollowerUser(Long user_followed_id, Long user_follower_id){
        UserFollower isAlreadyFollowed = userFollowerRepository.isAlreadyFollowed(user_followed_id, user_follower_id);
        if(isAlreadyFollowed == null) throw new BadRequestException("is Already not Unfollowed!");
        var followedOp = getUserById(user_followed_id);
        Optional<User> followerOp = getUserById(user_follower_id);
        if(followedOp.isEmpty() || followerOp.isEmpty()) throw new NotFoundException();
        User followed = followedOp.get();
        User follower = followerOp.get();
        followed.getFollowers().remove(follower);
        userRepository.save(followed);
    }

    public Optional<User> getUserById(Long id){
        return userRepository.findById(id);
    }
}
