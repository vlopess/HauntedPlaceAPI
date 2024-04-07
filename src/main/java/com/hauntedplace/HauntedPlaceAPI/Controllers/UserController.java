package com.hauntedplace.HauntedPlaceAPI.Controllers;





import com.hauntedplace.HauntedPlaceAPI.DTOS.UserDTO;
import com.hauntedplace.HauntedPlaceAPI.Repository.UserRepository;
import com.hauntedplace.HauntedPlaceAPI.Services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping(value = "/users")
    public List<UserDTO> getAllUsers() {
        return UserDTO.convert(userService.getAllUsers());
    }

    @PostMapping("/addUser")
    public ResponseEntity<Object> addUser(@RequestBody @Valid UserDTO userDTO) {
        userService.addUser(userDTO);
        return ResponseEntity.created(URI.create("/users")).body("User added!");
    }

    @PutMapping("{id}")
    public ResponseEntity<Object> updateUser(@PathVariable Long id, @RequestBody @Valid UserDTO userDTO) {
        userService.updateUser(id, userDTO);
        return ResponseEntity.ok().build();
    }
}
