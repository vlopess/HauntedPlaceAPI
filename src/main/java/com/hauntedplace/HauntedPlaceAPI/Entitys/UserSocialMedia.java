package com.hauntedplace.HauntedPlaceAPI.Entitys;

import jakarta.persistence.*;

@Entity(name = "user_social_medias")
public class UserSocialMedia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private SocialMedia socialMedia;
    @ManyToOne
    private User user;
    private String link;

    public UserSocialMedia() {
    }

    public UserSocialMedia(SocialMedia socialMedia, User user, String link) {
        this.socialMedia = socialMedia;
        this.user = user;
        this.link = link;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public SocialMedia getSocialMedia() {
        return socialMedia;
    }
    public void setSocialMedia(SocialMedia socialMedia) {
        this.socialMedia = socialMedia;
    }
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
    public String getLink() {
        return link;
    }
    public void setLink(String link) {
        this.link = link;
    }
}

