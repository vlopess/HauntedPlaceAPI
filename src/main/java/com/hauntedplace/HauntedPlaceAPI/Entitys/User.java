package com.hauntedplace.HauntedPlaceAPI.Entitys;
import com.hauntedplace.HauntedPlaceAPI.DTOS.LoginDTO;
import com.hauntedplace.HauntedPlaceAPI.DTOS.UserDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity(name = "users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String email;
    private String password;
    private String profilePictureUrl;
    private String bio;
    private String localization;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "users_tags",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private List<Tag> tags = new ArrayList<Tag>();

    @OneToMany(mappedBy = "user")
    private List<Post> posts = new ArrayList<Post>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_followers",
            joinColumns = @JoinColumn(name = "user_followed_id"),
            inverseJoinColumns = @JoinColumn(name = "user_follower_id")
    )
    private List<User> following = new ArrayList<User>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_followers",
            joinColumns = @JoinColumn(name = "user_follower_id"),
            inverseJoinColumns = @JoinColumn(name = "user_followed_id")
    )
    private List<User> followers = new ArrayList<User>();

    public User(){}

    public User(UserDTO userDTO) {
        this.id = userDTO.getId();
        this.username = userDTO.getUsername();
        this.email = userDTO.getEmail();
        this.password = userDTO.getPassword();
        this.profilePictureUrl = userDTO.getProfilePictureUrl();
        this.bio = userDTO.getBio();
        this.localization = userDTO.getLocalization();
        this.tags = userDTO.getTags();
        this.posts = userDTO.getPosts();
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
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

    public void addPost(Post post) {
        this.posts.add(post);
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }


    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
