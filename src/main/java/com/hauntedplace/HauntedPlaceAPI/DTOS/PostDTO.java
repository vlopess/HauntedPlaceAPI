package com.hauntedplace.HauntedPlaceAPI.DTOS;

import com.hauntedplace.HauntedPlaceAPI.Entitys.Post;
import com.hauntedplace.HauntedPlaceAPI.Entitys.User;
import com.hauntedplace.HauntedPlaceAPI.Models.EncryptedId;
import com.hauntedplace.HauntedPlaceAPI.Models.Enums.TagEnum;
import com.hauntedplace.HauntedPlaceAPI.Models.UserOverView;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;

import java.util.Date;

public class PostDTO {
    private String encryptedId;
    @NotBlank(message = "title is required")
    private String title;
    private String urlImage;
    private UserOverView user;
    private TagEnum tag;
    @NotBlank(message = "content is required")
    private String content;
    private Date createdAt;

    public PostDTO() {}

    public PostDTO(String encryptedId, String title, String urlImage, UserOverView user, TagEnum tag, String content, Date createdAt) {
        this.encryptedId = encryptedId;
        this.title = title;
        this.urlImage = urlImage;
        this.user = user;
        this.tag = tag;
        this.content = content;
        this.createdAt = createdAt;
    }

    public PostDTO(Post post){
        this.encryptedId = new EncryptedId(post.getId()).get();
        this.title = post.getTitle();
        this.urlImage = post.getUrlImage();
        this.user = new UserOverView(post.getUser());
        this.content = post.getContent();
        this.createdAt = post.getCreatedAt();
        this.tag = TagEnum.valueOf(post.getTag().getDescription());
    }

//    public Long getId() {
//        return new EncryptedId(encryptedId).getDecrypted();
//    }

    public String getEncryptedId() {
        return encryptedId;
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
    public UserOverView getUser() {
        return user;
    }

    public void setUser(UserOverView user) {
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
