package com.hauntedplace.HauntedPlaceAPI.Services;

import com.hauntedplace.HauntedPlaceAPI.DTOS.PostDTO;
import com.hauntedplace.HauntedPlaceAPI.Repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class FeedService {
    private final PostRepository postRepository;

    @Autowired
    public FeedService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public ResponseEntity<List<Object>> getPosts(Long user_followed_id) {
        var posts = postRepository.getFeed(user_followed_id);
        if (posts.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(Collections.singletonList(posts.stream().map(PostDTO::new).toList()));
    }
}
