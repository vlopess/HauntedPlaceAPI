package com.hauntedplace.HauntedPlaceAPI.Controllers;

import com.hauntedplace.HauntedPlaceAPI.Entitys.Tag;
import com.hauntedplace.HauntedPlaceAPI.Services.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.hauntedplace.HauntedPlaceAPI.Models.Enums.TagEnum;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/tags")
public class TagController {
    private final TagService tagService;

    @Autowired
    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping
    public ResponseEntity<List<Tag>> getAllTags() {
        var tags = tagService.getAllTags();
        return ResponseEntity.ok(tags);
    }

}
