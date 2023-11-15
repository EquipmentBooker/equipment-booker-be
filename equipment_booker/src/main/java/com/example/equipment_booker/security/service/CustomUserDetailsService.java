package com.example.equipment_booker.security.service;

import com.example.equipment_booker.model.RegisteredUser;
import com.example.equipment_booker.service.RegisteredUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.Collection;

@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final RegisteredUserService registeredUserService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        RegisteredUser registeredUser = registeredUserService.findByEmail(username);
        if (registeredUser == null) {
            throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username));
        } else {
            return new UserDetails() {
                @Override
                public Collection<? extends GrantedAuthority> getAuthorities() {
                    var authorities = new ArrayList<GrantedAuthority>();
                    if (registeredUser != null) {
                        authorities.add(new SimpleGrantedAuthority("ROLE_REGISTERED_USER"));
                    }

                    return authorities;
                }

                @Override
                public String getPassword() {
                    if (registeredUser != null) {
                        return registeredUser.getPassword();
                    }
                    return registeredUser.getPassword();
                }

                @Override
                public String getUsername() {
                    if (registeredUser != null) {
                        return registeredUser.getEmail();
                    }
                    return registeredUser.getEmail();
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
            };
        }
    }
}
