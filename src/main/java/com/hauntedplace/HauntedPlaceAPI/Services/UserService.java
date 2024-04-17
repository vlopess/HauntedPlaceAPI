package com.hauntedplace.HauntedPlaceAPI.Services;

import com.hauntedplace.HauntedPlaceAPI.DTOS.UserDTO;
import com.hauntedplace.HauntedPlaceAPI.Entitys.User;
import com.hauntedplace.HauntedPlaceAPI.Repository.UserRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;


@Service
public class UserService {
    protected final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    public Page<User> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    public User addUser(UserDTO userDTO) {
        User user = new User(userDTO);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public ResponseEntity<UserDTO> updateUser(Long id, UserDTO userDTO) {
        Optional<User> user = getUserById(id);
        if (user.isPresent()) {
            userDTO.setId(id);
            User newUser = new User(userDTO);
            userRepository.save(newUser);
//            Na forma abaixo funciona, no entanto, por não saber algo o atributo modificado, teriamos que setar um por um
//            User newUser = user.get();
//            newUser.setUsername(userDTO.getUsername());
//            newUser.setEmail(userDTO.getEmail());
//            newUser.setPassword(userDTO.getPassword());
            return ResponseEntity.ok(new UserDTO(newUser));
        }
        return ResponseEntity.notFound().build();
    }

    public ResponseEntity<Object> deleteUser(Long id){
        Optional<User> user = getUserById(id);
        if (user.isPresent()) {
            userRepository.delete(user.get());
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
