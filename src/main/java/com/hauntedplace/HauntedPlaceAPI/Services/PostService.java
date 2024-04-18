package com.hauntedplace.HauntedPlaceAPI.Services;


import com.hauntedplace.HauntedPlaceAPI.DTOS.LoginDTO;
import com.hauntedplace.HauntedPlaceAPI.DTOS.PostDTO;
import com.hauntedplace.HauntedPlaceAPI.DTOS.UserDTO;
import com.hauntedplace.HauntedPlaceAPI.Entitys.Post;
import com.hauntedplace.HauntedPlaceAPI.Entitys.User;
import com.hauntedplace.HauntedPlaceAPI.Repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

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

    public ResponseEntity<PostDTO> getPostById(Long id) {
        var post = postRepository.findById(id).orElse(null);
        if (post == null) return  ResponseEntity.notFound().build();
        return  ResponseEntity.ok(new PostDTO(post));
    }

    public ResponseEntity<List<PostDTO>> getPostAllUser(String username) {
        var posts = postRepository.findByUserUsername(username);
        if (posts == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(posts.stream().map(PostDTO::new).toList());
    }

    public ResponseEntity<PostDTO> update(Long id, PostDTO postDTO) {
        Optional<Post> post = postRepository.findById(id);
        if (post.isEmpty()) return ResponseEntity.notFound().build();
        postDTO.setId(id);
        Post newPost = new Post(postDTO);
        postRepository.save(newPost);
        return ResponseEntity.ok(postDTO);
    }

    public ResponseEntity<Object> deletePost(Long id){
        Optional<Post> post = postRepository.findById(id);
        if (post.isEmpty()) return ResponseEntity.notFound().build();
        postRepository.delete(post.get());
        return ResponseEntity.ok().build();
    }

}
