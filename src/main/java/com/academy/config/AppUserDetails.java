package com.academy.config;

import com.academy.domain.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/** The logged-in user as stored in the security session. */
public class AppUserDetails implements UserDetails {

    private final Long id;
    private final String email;
    private final String fullName;
    private final String passwordHash;
    private final boolean admin;
    private final boolean active;

    public AppUserDetails(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.fullName = user.getFullName();
        this.passwordHash = user.getPasswordHash();
        this.admin = user.isAdmin();
        this.active = user.isActive();
    }

    public Long getId() { return id; }
    public String getFullName() { return fullName; }
    public boolean isAdmin() { return admin; }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(admin ? "ROLE_ADMIN" : "ROLE_STUDENT"));
    }

    @Override
    public String getPassword() { return passwordHash; }

    @Override
    public String getUsername() { return email; }

    @Override
    public boolean isEnabled() { return active; }
}
