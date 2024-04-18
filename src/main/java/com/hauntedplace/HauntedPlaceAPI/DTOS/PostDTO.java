package com.hauntedplace.HauntedPlaceAPI.DTOS;

import com.hauntedplace.HauntedPlaceAPI.Entitys.Post;
import com.hauntedplace.HauntedPlaceAPI.Entitys.User;
import com.hauntedplace.HauntedPlaceAPI.Models.Enums.TagEnum;
import jakarta.persistence.ManyToOne;

import java.util.Date;

public class PostDTO {
    private Long id;
    private String title;
    private String urlImage;
    private User user;
    private TagEnum tag;
    private String content;
    private Date createdAt;

    public PostDTO() {}

    public PostDTO(Long id, String title, String urlImage, User user, TagEnum tag, String content, Date createdAt) {
        this.id = id;
        this.title = title;
        this.urlImage = urlImage;
        this.user = user;
        this.tag = tag;
        this.content = content;
        this.createdAt = createdAt;
    }

    public PostDTO(Post post){
        this.id = post.getId();
        this.title = post.getTitle();
        this.urlImage = post.getUrlImage();
        this.user = post.getUser();
        this.tag = post.getTag();
        this.content = post.getContent();
        this.createdAt = post.getCreatedAt();
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
    public TagEnum getTag() {
        return tag;
    }

    public void setTag(TagEnum tag) {
        this.tag = tag;
    }

    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public Date getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
