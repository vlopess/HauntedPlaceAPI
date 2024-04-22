package com.hauntedplace.HauntedPlaceAPI.Controllers;

import com.hauntedplace.HauntedPlaceAPI.DTOS.PostDTO;
import com.hauntedplace.HauntedPlaceAPI.Services.PostService;
import jakarta.validation.Valid;
import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/post")
public class PostController {

    private final PostService postService;

    @Autowired
    public PostController(PostService postService){
        this.postService = postService;

    }

    @PostMapping("/publicar")
    public ResponseEntity<String> postar(@RequestBody @Valid PostDTO postDTO, UriComponentsBuilder uriBuilder){
        try {
            var post = postService.save(postDTO);
            URI uri = uriBuilder.path("/post/{id}").buildAndExpand(post.getEncryptedId()).toUri();
            return ResponseEntity.created(uri).body("Post created!");
        }catch (Exception e){
            return ResponseEntity.internalServerError().body(e.getMessage());
        }

    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getPostById(@PathVariable Long id){
        try{
            return postService.getPostById(id);
        }catch (Exception e){
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @GetMapping("/user/{username}")
    public ResponseEntity<List<Object>> getPostById(@PathVariable String username){
        try{
            return postService.getAllUserPosts(username);
        }catch (Exception e){
            return ResponseEntity.internalServerError().body(Collections.singletonList(e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updatePost(@PathVariable Long id, @RequestBody PostDTO postDTO){
        try{
            return postService.update(id, postDTO);
        }catch (Exception e){
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deletePost(@PathVariable Long id){
        try{
            return postService.deletePost(id);
        }catch (Exception e){
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
}
