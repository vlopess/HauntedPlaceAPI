package com.hauntedplace.HauntedPlaceAPI.Services;

import com.hauntedplace.HauntedPlaceAPI.DTOS.UserDTO;
import com.hauntedplace.HauntedPlaceAPI.Entitys.User;
import com.hauntedplace.HauntedPlaceAPI.Repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    protected final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void addUser(UserDTO userDTO) {
        User user = new User(userDTO);
        userRepository.save(user);
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public void updateUser(Long id, UserDTO userDTO) {
        User user = getUserById(id);
        if (user != null) {
            User newUser = new User(userDTO);

            userRepository.save(newUser);
        }
    }

}
