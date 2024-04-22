package com.hauntedplace.HauntedPlaceAPI.Controllers;

import com.hauntedplace.HauntedPlaceAPI.Services.FeedService;
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
    public ResponseEntity<List<Object>> getFeed(@RequestHeader Long user_followed_id) {
        try{
            return feedService.getPosts(user_followed_id);
        } catch (Exception e){
            return ResponseEntity.internalServerError().body(Collections.singletonList(e.getMessage()));
        }
    }

}
