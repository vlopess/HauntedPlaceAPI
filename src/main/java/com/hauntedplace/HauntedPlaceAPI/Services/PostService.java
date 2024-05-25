package com.hauntedplace.HauntedPlaceAPI.Services;


import com.hauntedplace.HauntedPlaceAPI.DTOS.PostDTO;
import com.hauntedplace.HauntedPlaceAPI.Entitys.Post;
import com.hauntedplace.HauntedPlaceAPI.Entitys.User;
import com.hauntedplace.HauntedPlaceAPI.Repository.PostRepository;
import com.hauntedplace.HauntedPlaceAPI.Security.infra.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {
    private final PostRepository postRepository;

    @Autowired
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public PostDTO save(PostDTO post) {
        var newPost = new Post(post);
        postRepository.save(newPost);
        return post;
    }

    public Post getPostById(Long id) throws Exception {
        var post = postRepository.findById(id);
        return unwrap(post);
    }

    public List<Post> getAllUserPosts(String username) {
        return postRepository.findByUserUsername(username);
    }

    public Object update(Long id, PostDTO postDTO) throws Exception {
        Post post = unwrap(postRepository.findById(id));
        Post newPost = new Post(postDTO);
        newPost.setId(id);
        postRepository.save(newPost);
        return postDTO;
    }

    public void deletePost(Long id) throws Exception {
        Post post = unwrap(postRepository.findById(id));
        postRepository.delete(post);
    }

    static Post unwrap(Optional<Post> post) throws Exception {
        if(post.isPresent()) return post.get();
        throw new NotFoundException("Post not found");
    }
}
