package com.hauntedplace.HauntedPlaceAPI.Controllers;





import com.hauntedplace.HauntedPlaceAPI.DTOS.UserDTO;
import com.hauntedplace.HauntedPlaceAPI.Entitys.User;
import com.hauntedplace.HauntedPlaceAPI.Repository.UserRepository;
import com.hauntedplace.HauntedPlaceAPI.Services.UserService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
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
    public Page<UserDTO> getAllUsers(Pageable pageable) {
        return UserDTO.convert(userService.getAllUsers(pageable));
    }

    @PostMapping("/addUser")
    public ResponseEntity<Object> addUser(@RequestBody @Valid UserDTO userDTO, UriComponentsBuilder uriBuilder) {
        User user = userService.addUser(userDTO);
        URI uri = uriBuilder.path("/user/{id}").buildAndExpand(user.getId()).toUri();
        return ResponseEntity.created(uri).body("User added!");
    }

    @PutMapping("{id}")
    @Transactional
    public ResponseEntity<Object> updateUser(@PathVariable Long id, @RequestBody UserDTO userDTO) {
        return userService.updateUser(id, userDTO);
    }


    @DeleteMapping("{id}")
    @Transactional
    public ResponseEntity<Object> deleteUser(@PathVariable Long id){
        return userService.deleteUser(id);
    }
}
