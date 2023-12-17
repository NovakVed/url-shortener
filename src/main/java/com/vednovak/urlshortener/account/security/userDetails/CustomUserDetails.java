package com.vednovak.urlshortener.account.security.userDetails;

import com.vednovak.urlshortener.account.enums.UserRole;
import com.vednovak.urlshortener.account.models.Account;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public record CustomUserDetails(String username, String password, Collection<? extends GrantedAuthority> authorities)
        implements UserDetails {

    public static CustomUserDetails fromAccount(Account account) {
        return new CustomUserDetails(
                account.getAccountId(),
                account.getPassword(),
                Collections.singleton(new SimpleGrantedAuthority(UserRole.ROLE_USER.name()))
        );
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
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
