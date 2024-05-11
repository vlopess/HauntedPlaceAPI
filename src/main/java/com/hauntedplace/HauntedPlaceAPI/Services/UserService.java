package com.hauntedplace.HauntedPlaceAPI.Services;


import com.hauntedplace.HauntedPlaceAPI.DTOS.UserDTO;
import com.hauntedplace.HauntedPlaceAPI.Entitys.User;
import com.hauntedplace.HauntedPlaceAPI.Models.EncryptedId;
import com.hauntedplace.HauntedPlaceAPI.Models.UserDetail;
import com.hauntedplace.HauntedPlaceAPI.Repository.UserRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
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

    public ResponseEntity<UserDetail> getUserbyId(String encryptedId) {
        Long id = new EncryptedId(encryptedId).getDecrypted();
        Optional<User> user = userRepository.findById(id);
        return user.map(value -> ResponseEntity.ok(new UserDetail(value))).orElseGet(() -> ResponseEntity.notFound().build());
    }

    public ResponseEntity<Object> getUserByUsername(String username) {
        UserDetails user = userRepository.findByUsername(username);
        if(user == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(new UserDetail((User) user));
    }

    public User addUser(UserDTO userDTO) {
        User user = new User(userDTO);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public ResponseEntity<Object> updateUser(Long id, UserDTO userDTO) {
        Optional<User> user = getUserById(id);
        if (user.isPresent()) {
            User newUser = user.get();
            newUser.setUsername(userDTO.getUsername());
            newUser.setEmail(userDTO.getEmail());
            newUser.setLocalization(userDTO.getLocalization());
            newUser.setBio(userDTO.getBio());
            newUser.setProfilePictureUrl(userDTO.getProfilePictureUrl());
            newUser.getTags().clear();
            userDTO.getTags().forEach(newUser::addTag);
            newUser.getSocialMedias().clear();
            userDTO.getSocialMedias().forEach(newUser.getSocialMedias()::add);
            newUser = userRepository.saveAndFlush(newUser);
            return ResponseEntity.ok(new UserDetail(newUser));
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

