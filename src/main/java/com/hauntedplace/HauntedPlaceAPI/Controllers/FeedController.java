package com.hauntedplace.HauntedPlaceAPI.Controllers;

import com.hauntedplace.HauntedPlaceAPI.DTOS.PostDTO;
import com.hauntedplace.HauntedPlaceAPI.Services.FeedService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/feed")
public class FeedController {
    final private FeedService feedService;

    @Autowired
    public FeedController(FeedService feedService) {
        this.feedService = feedService;
    }

    @GetMapping
    @Operation(summary = "Autorização necessária", security = @SecurityRequirement(name = "bearer-key"))
    public ResponseEntity<List<Object>> getFeed(@RequestHeader Long user_followed_id) {
        var posts = feedService.getPosts(user_followed_id);
        return ResponseEntity.ok(Collections.singletonList(posts.stream().map(PostDTO::new).toList()));

    }

}
