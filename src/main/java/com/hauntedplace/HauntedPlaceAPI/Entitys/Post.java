package com.hauntedplace.HauntedPlaceAPI.Entitys;

import com.hauntedplace.HauntedPlaceAPI.DTOS.PostDTO;
import com.hauntedplace.HauntedPlaceAPI.Models.Enums.TagEnum;
import jakarta.persistence.*;

import java.util.Date;

@Entity(name = "posts")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String urlImage;
    @ManyToOne
    private User user;
    @ManyToOne
    private Tag tag;
    private String content;
    private Date createdAt;

    public Post() {}

    public Post(Long id, String title, String urlImage, User user, Tag tag, String content, Date createdAt) {
        this.id = id;
        this.title = title;
        this.urlImage = urlImage;
        this.user = user;
        this.tag = tag;
        this.content = content;
        this.createdAt = createdAt;
    }

    public Post(PostDTO postDTO){
        this.id = postDTO.getId();
        this.title = postDTO.getTitle();
        this.urlImage = postDTO.getUrlImage();
        this.user = new User(postDTO.getUser());
        this.content = postDTO.getContent();
        this.tag = new Tag(postDTO.getTag());
        this.createdAt = postDTO.getCreatedAt();
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
    public Tag getTag() {
        return tag;
    }

    public void setTag(Tag tag) {
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
