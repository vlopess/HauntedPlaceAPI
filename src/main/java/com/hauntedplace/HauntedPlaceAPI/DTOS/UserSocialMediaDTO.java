package com.hauntedplace.HauntedPlaceAPI.DTOS;

import com.hauntedplace.HauntedPlaceAPI.Entitys.SocialMedia;
import com.hauntedplace.HauntedPlaceAPI.Entitys.User;
import com.hauntedplace.HauntedPlaceAPI.Entitys.UserSocialMedia;
import com.hauntedplace.HauntedPlaceAPI.Models.UserOverView;
import jakarta.persistence.ManyToOne;

public class UserSocialMediaDTO {

    private Long id;
    private SocialMedia socialMedia;
    private String link;

    public UserSocialMediaDTO() {
    }

    public UserSocialMediaDTO(SocialMedia socialMedia, UserOverView user, String link) {
        this.socialMedia = socialMedia;
        this.link = link;
    }
    public UserSocialMediaDTO(UserSocialMedia userSocialMedia) {
        this.id = userSocialMedia.getId();
        this.socialMedia = userSocialMedia.getSocialMedia();
        this.link = userSocialMedia.getLink();
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
    public String getLink() {
        return link;
    }
    public void setLink(String link) {
        this.link = link;
    }
}
