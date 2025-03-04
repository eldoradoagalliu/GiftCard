package com.giftcard.model;

import com.google.firebase.database.PropertyName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails {

    private String fullName;
    private String email;
    private String password;
    private String role;
    private String company;
    private String branchName;
    private String phoneNumber;
    private String location;
    private Date createdAt;
    private Date updatedAt;

    @PropertyName("username")
    @Override
    public String getUsername() {
        return this.email;
    }

    @PropertyName("authorities")
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role));
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }

    /**
     * Setters for account status properties -> Used to avoid warning logs while mapping these fields
     */
    public void setUsername(String username) {
    }

    public void setAccountNonExpired(boolean accountNonExpired) {
    }

    public void setAccountNonLocked(boolean accountNonLocked) {
    }

    public void setCredentialsNonExpired(boolean credentialsNonExpired) {
    }

    public void setEnabled(boolean enabled) {
    }
}
