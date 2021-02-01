package io.jzheaux.springsecurity.resolutions;

import javax.persistence.*;
import java.util.UUID;

@Entity(name = "authorities")
public class UserAuthority {

    @Id
    private UUID id;

    @Column
    private String authority;

    @JoinColumn(name="username", referencedColumnName="username")
    @ManyToOne
    private User user;

    public UserAuthority() {
    }

    public UserAuthority(User user, String authority) {
        this.id = UUID.randomUUID();
        this.user = user;
        this.authority = authority;
    }

    public UserAuthority(UserAuthority other) {
        this.id = other.id;
        this.authority = other.authority;
        this.user = other.user;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
