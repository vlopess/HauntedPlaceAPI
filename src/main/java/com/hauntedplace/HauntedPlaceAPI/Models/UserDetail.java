package com.hauntedplace.HauntedPlaceAPI.Models;

import com.hauntedplace.HauntedPlaceAPI.DTOS.PostDTO;
import com.hauntedplace.HauntedPlaceAPI.DTOS.UserDTO;
import com.hauntedplace.HauntedPlaceAPI.DTOS.UserSocialMediaDTO;
import com.hauntedplace.HauntedPlaceAPI.Entitys.Post;
import com.hauntedplace.HauntedPlaceAPI.Entitys.Tag;
import com.hauntedplace.HauntedPlaceAPI.Entitys.User;
import com.hauntedplace.HauntedPlaceAPI.Entitys.UserSocialMedia;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.ArrayList;
import java.util.List;

public class UserDetail {
    private String id;
    private String username;
    private String email;
    private String profilePictureUrl;
    private String bio;
    private String localization;
    private Boolean isFollowed;
    private List<Tag> tags = new ArrayList<Tag>();
    private List<PostDTO> posts = new ArrayList<PostDTO>();
    private List<UserOverView> followers = new ArrayList<UserOverView>();
    private List<UserOverView> following = new ArrayList<UserOverView>();
    private List<UserSocialMediaDTO> socialMedias = new ArrayList<UserSocialMediaDTO>();



    public UserDetail(){}

    public UserDetail(User user) {
        this.id = new EncryptedId(user.getId()).get();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.profilePictureUrl = user.getProfilePictureUrl();
        this.bio = user.getBio();
        this.localization = user.getLocalization();
        this.tags = user.getTags();
        this.posts = user.getPosts().stream().map(PostDTO::new).toList();
        this.followers = user.getFollowers().stream().map(UserOverView::new).toList();
        this.following = user.getFollowing().stream().map(UserOverView::new).toList();
        this.socialMedias = user.getSocialMedias().stream().map(UserSocialMediaDTO::new).toList();
    }

    public String getId() {
        return id;
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

    public List<PostDTO> getPosts() {
        return posts;
    }
    public void setPosts(List<PostDTO> posts) {
        this.posts = posts;
    }

    public List<UserOverView> getFollowers(){
        return this.followers;
    }
    public List<UserOverView> getFollowing(){
        return this.following;
    }
    public List<UserSocialMediaDTO> getSocialMedias(){
        return this.socialMedias;
    }
    public void setSocialMedias(List<UserSocialMediaDTO> socialMedias) {
        this.socialMedias = socialMedias;
    }
}

