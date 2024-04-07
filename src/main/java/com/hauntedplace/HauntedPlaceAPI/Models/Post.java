package com.hauntedplace.HauntedPlaceAPI.Models;

import com.hauntedplace.HauntedPlaceAPI.Entitys.User;
import com.hauntedplace.HauntedPlaceAPI.Models.Enums.Category;

public class Post {
    private Long id;
    private String title;
    private String urlImage;
    private User user;
    private Category category;

    public Post() {}

    public Post(Long id, String title, String urlImage, User user, Category category) {
        this.id = id;
        this.title = title;
        this.urlImage = urlImage;
        this.user = user;
        this.category = category;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrlImage() {
        return urlImage;
    }
    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
