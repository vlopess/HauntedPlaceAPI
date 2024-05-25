package com.hauntedplace.HauntedPlaceAPI.Controllers;

import com.hauntedplace.HauntedPlaceAPI.DTOS.PostDTO;
import com.hauntedplace.HauntedPlaceAPI.Services.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import java.net.URI;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/post")
public class PostController {

    private final PostService postService;

    @Autowired
    public PostController(PostService postService){
        this.postService = postService;

    }

    @PostMapping("/publicar")
    @Operation(summary = "Autorização necessária", security = @SecurityRequirement(name = "bearer-key"))
    public ResponseEntity<String> postar(@RequestBody @Valid PostDTO postDTO, UriComponentsBuilder uriBuilder){
        var post = postService.save(postDTO);
        URI uri = uriBuilder.path("/post/{id}").buildAndExpand(post.getEncryptedId()).toUri();
        return ResponseEntity.created(uri).body("Post created!");
    }

    @GetMapping("/{id}")
    @Operation(summary = "Autorização necessária", security = @SecurityRequirement(name = "bearer-key"))
    public ResponseEntity<Object> getPostById(@PathVariable Long id) throws Exception {
        var post = postService.getPostById(id);
        return  ResponseEntity.ok(new PostDTO(post));

    }

    @GetMapping("/user/{username}")
    @Operation(summary = "Autorização necessária", security = @SecurityRequirement(name = "bearer-key"))
    public ResponseEntity<List<Object>> getPostById(@PathVariable String username){
        var posts = postService.getAllUserPosts(username);
        return ResponseEntity.ok(Collections.singletonList(posts.stream().map(PostDTO::new).toList()));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Autorização necessária", security = @SecurityRequirement(name = "bearer-key"))
    public ResponseEntity<Object> updatePost(@PathVariable Long id, @RequestBody PostDTO postDTO) throws Exception {
        var post = postService.update(id, postDTO);
        return ResponseEntity.ok(post);

    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Autorização necessária", security = @SecurityRequirement(name = "bearer-key"))
    public ResponseEntity<Object> deletePost(@PathVariable Long id) throws Exception {
        postService.deletePost(id);
        return ResponseEntity.ok("delete was successful!");

    }
}
