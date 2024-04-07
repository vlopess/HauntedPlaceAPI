package com.hauntedplace.HauntedPlaceAPI.DTOS;


import com.hauntedplace.HauntedPlaceAPI.Entitys.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;



import java.util.List;

public class UserDTO {
    private Long id;
    @NotBlank(message="Enter name")
    private String name;
    @Email
    private String email;
    @NotBlank(message="Enter password")
    private String password;

    public UserDTO(User user) {
        this(user.getId(),user.getEmail(), user.getName(), user.getPassword());
    }

    public UserDTO(Long id, String name, String email, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;

    }

    public Long getId() {
        return id;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name){
        this.name = name;
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

    public static List<UserDTO> convert(List<User> users) {
        return users.stream().map(UserDTO::new).toList();
                //.collect(Collectors.toList());
    }

}

