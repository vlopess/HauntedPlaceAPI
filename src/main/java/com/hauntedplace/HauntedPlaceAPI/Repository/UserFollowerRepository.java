package com.hauntedplace.HauntedPlaceAPI.Repository;

import com.hauntedplace.HauntedPlaceAPI.Entitys.UserFollower;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserFollowerRepository extends JpaRepository<UserFollower,Long> {

    @Query("select uf from users_followers uf where uf.user_followed_id = :user_followed_id AND uf.user_follower_id = :user_follower_id")
    UserFollower isAlreadyFollowed(@Param("user_followed_id") Long user_followed_id, @Param("user_follower_id") Long user_follower_id);
}
