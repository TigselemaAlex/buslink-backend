package com.cdg.buslinkbackend.security.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * This class implements the UserDetails interface and is used to store the
 * user's information in the
 * security context
 */
@Getter
@Setter
@Builder
public class UserPrincipal implements UserDetails {

    private String id;

    private String username;

    private String password;

    private boolean status;

    private Collection<? extends GrantedAuthority> authorities;

    /**
     * This function returns a collection of GrantedAuthority objects that represent
     * the roles that the
     * user has.
     * 
     * @return A collection of GrantedAuthority objects.
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    /**
     * This function returns the password of the user
     * 
     * @return The password.
     */
    @Override
    public String getPassword() {
        return password;
    }

    /**
     * This function returns the username of the user
     * 
     * @return The username
     */
    @Override
    public String getUsername() {
        return username;
    }

    /**
     * This function returns true if the account is not expired.
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * This function returns true if the account is not locked.
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * This function returns true if the user's credentials are not expired.
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * This function returns true if the user is enabled, and false if the user is
     * disabled.
     * 
     * @return The status of the user.
     */
    @Override
    public boolean isEnabled() {
        return status;
    }
}
