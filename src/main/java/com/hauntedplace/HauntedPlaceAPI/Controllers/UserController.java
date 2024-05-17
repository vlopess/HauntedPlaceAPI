package com.hauntedplace.HauntedPlaceAPI.Controllers;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.hauntedplace.HauntedPlaceAPI.DTOS.LoginDTO;
import com.hauntedplace.HauntedPlaceAPI.DTOS.UserDTO;
import com.hauntedplace.HauntedPlaceAPI.Entitys.User;
import com.hauntedplace.HauntedPlaceAPI.Models.StringWrapper;
import com.hauntedplace.HauntedPlaceAPI.Models.UserDetail;
import com.hauntedplace.HauntedPlaceAPI.Services.FirebaseStorageService;
import com.hauntedplace.HauntedPlaceAPI.Services.UserFollowerService;
import com.hauntedplace.HauntedPlaceAPI.Services.UserService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.File;
import java.net.URI;
import java.util.Arrays;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    private final FirebaseStorageService firebaseStorageService;
    private final UserFollowerService userFollowerService;

    @Autowired
    public UserController(UserService userService, UserFollowerService userFollowerService, FirebaseStorageService firebaseStorageService) {
        this.userService = userService;
        this.userFollowerService = userFollowerService;
        this.firebaseStorageService = firebaseStorageService;
    }


    @GetMapping("/users")
    public Page<LoginDTO> getAllUsers(Pageable pageable) {
        return LoginDTO.convert(userService.getAllUsers(pageable));
    }

    @GetMapping
    public ResponseEntity<UserDetail> getUserById(@RequestParam String encryptedId) {
        return userService.getUserbyId(encryptedId);
    }

    @GetMapping(value = "/{username}")
    public ResponseEntity<Object> getUserByUsername(@PathVariable String username) {
        return userService.getUserByUsername(username);
    }

    @PostMapping("/register")
    @Transactional
    public ResponseEntity<Object> addUser(@RequestBody @Valid UserDTO userDTO, UriComponentsBuilder uriBuilder) throws Exception {
        try {
            User user = userService.addUser(userDTO);
            URI uri = uriBuilder.path("/user/{id}").buildAndExpand(user.getId()).toUri();
            return ResponseEntity.created(uri).body(new StringWrapper("User added!"));
        }catch (DataIntegrityViolationException e){
            if(userDTO.getProfilePictureUrl() != null){
                firebaseStorageService.remove(getFileName(userDTO.getProfilePictureUrl()));
            }
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    private String getFileName(String profilePictureUrl) {
        var strings = profilePictureUrl.split("/");
        var filename =  Arrays.stream(strings).filter(s -> s.contains(".png") || s.contains(".jpeg") || s.contains(".jpg")).findFirst().get();
        return filename.replace("?alt=media", "");
    }

    @PostMapping("/{user_follower_id}/follower/{user_followed_id}")
    public ResponseEntity<String> followerUser(@PathVariable Long user_followed_id, @PathVariable  Long user_follower_id){
        return userFollowerService.followerUser(user_followed_id, user_follower_id);
    }

    @DeleteMapping("/{user_follower_id}/unfollow/{user_followed_id}")
    public ResponseEntity<String> unFollowerUser(@PathVariable Long user_followed_id, @PathVariable  Long user_follower_id){
        return userFollowerService.unFollowerUser(user_followed_id, user_follower_id);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<Object> updateUser(@PathVariable Long id, @RequestBody UserDTO userDTO) {
        return userService.updateUser(id, userDTO);
    }


    @DeleteMapping("/{id}")
    @Transactional
    @Secured("ROLE_ADMIN")
    public ResponseEntity<Object> deleteUser(@PathVariable Long id){
        return userService.deleteUser(id);
    }
}
