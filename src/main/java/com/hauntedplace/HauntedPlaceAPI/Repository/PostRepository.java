package com.hauntedplace.HauntedPlaceAPI.Repository;

import com.hauntedplace.HauntedPlaceAPI.Entitys.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    public List<Post> findByUserUsername(String username);

    @Query("select p from users_followers uf, users u, posts p where uf.user_followed_id = u.id AND uf.user_follower_id = :user_followed_id AND p.user.id = u.id")
    public List<Post> getFeed(@Param("user_followed_id") Long user_followed_id);

}
