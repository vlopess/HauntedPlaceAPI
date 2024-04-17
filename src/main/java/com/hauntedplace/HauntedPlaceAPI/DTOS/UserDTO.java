package com.hauntedplace.HauntedPlaceAPI.DTOS;


import com.hauntedplace.HauntedPlaceAPI.Entitys.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.domain.Page;

public class UserDTO {
    private Long id;
    @NotBlank(message="Enter username")
    private String username;
    @Email
    private String email;
    @NotBlank(message="Enter password")
    private String password;

    public UserDTO(User user) {
        this(user.getId(),user.getUsername(), user.getEmail(), user.getPassword());
    }

    public UserDTO(Long id, String username, String email, String password) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;

    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getUsername() {
        return this.username;
    }
    public void setUsername(String username){
        this.username = username;
    }
    public String getEmail() {
        return this.email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return this.password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public static Page<UserDTO> convert(Page<User> users) {
        return users.map(UserDTO::new);
                //.collect(Collectors.toList());
    }

}

