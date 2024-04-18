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
        var post = postService.save(postDTO);
        URI uri = uriBuilder.path("/post/{id}").buildAndExpand(postDTO.getId()).toUri();
        return ResponseEntity.created(uri).body("Post created!");
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDTO> getPostById(@PathVariable Long id){
        return postService.getPostById(id);
    }

    @GetMapping("/user/{username}")
    public ResponseEntity<List<PostDTO>> getPostById(@PathVariable String username){
        return postService.getPostAllUser(username);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostDTO> updatePost(@PathVariable Long id, @RequestBody PostDTO postDTO){
        return postService.update(id, postDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deletePost(@PathVariable Long id){
        return postService.deletePost(id);
    }
}
