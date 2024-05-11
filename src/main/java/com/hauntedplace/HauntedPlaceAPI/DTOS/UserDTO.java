package com.hauntedplace.HauntedPlaceAPI.DTOS;

import com.hauntedplace.HauntedPlaceAPI.Entitys.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

public class UserDTO {
    private Long id;
    @NotBlank(message="Enter username")
    private String username;
    @Email
    private String email;
    @NotBlank(message="Enter password")
    private String password;
    private MultipartFile profilePicture;
    private String profilePictureUrl;
    private String bio;
    private String localization;
    private List<Tag> tags = new ArrayList<>();
    private List<Post> posts = new ArrayList<>();
    private List<UserSocialMedia> socialMedias = new ArrayList<>();

    public UserDTO(){}

    public UserDTO(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.profilePictureUrl = user.getProfilePictureUrl();
        this.bio = user.getBio();
        this.localization = user.getLocalization();
        this.tags = user.getTags();
//        this.posts = user.getPosts();
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getUsername() {
        return username;
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

    public List<Tag> getTags() {
        return this.tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public void addTag(Tag tag) {
        this.tags.add(tag);
    }

    public String getProfilePictureUrl() {
        return profilePictureUrl;
    }

    public void setProfilePictureUrl(String profilePictureUrl) {
        this.profilePictureUrl = profilePictureUrl;
    }
    public String getBio() {
        return bio;
    }
    public void setBio(String bio) {
        this.bio = bio;
    }
    public String getLocalization() {
        return localization;
    }
    public void setLocalization(String localization) {
        this.localization = localization;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public List<Post> getPosts() {
        return posts;
    }
    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }
    public List<UserSocialMedia> getSocialMedias() {
        return socialMedias;
    }
    public void setSocialMedias(List<UserSocialMedia> socialMedias) {
        this.socialMedias = socialMedias;
    }
}
