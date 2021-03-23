package io.jzheaux.springsecurity.resolutions;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;
import java.util.stream.Collectors;

@Entity(name = "users")
public class User implements Serializable {

    @Id
    protected UUID id;

    @Column
    protected String username;

    @Column
    protected String password;

    @Column
    protected boolean enabled = true;

    @Column(name = "full_name")
    protected String fullName;

    @Column
    String subscription;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    protected Collection<UserAuthority> userAuthorities = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    Collection<User> friends = new ArrayList<>();

    public User() {
    }

    public User(String username, String password) {
        this.id = UUID.randomUUID();
        this.username = username;
        this.password = password;
    }

    public User(User user) {
        this.id = user.id;
        this.username = user.username;
        this.password = user.password;
        this.enabled = user.enabled;
        this.fullName = user.fullName;
        this.userAuthorities = user.userAuthorities.stream().map(UserAuthority::new).collect(Collectors.toList());
        this.friends = user.friends.stream().map(User::new).collect(Collectors.toList());
    }

    public void grantAuthority(String authority) {
        UserAuthority userAuthority = new UserAuthority(this, authority);
        this.userAuthorities.add(userAuthority);
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getSubscription() {
        return subscription;
    }

    public void setSubscription(String subscription) {
        this.subscription = subscription;
    }

    public Collection<UserAuthority> getUserAuthorities() {
        return userAuthorities;
    }

    public void setUserAuthorities(Collection<UserAuthority> userAuthorities) {
        this.userAuthorities = userAuthorities;
    }

    public void addFriend(User friend) {
        friends.add(friend);
    }

    public Collection<User> getFriends() {
        return friends;
    }

    public void setFriends(Collection<User> friends) {
        this.friends = friends;
    }
}
