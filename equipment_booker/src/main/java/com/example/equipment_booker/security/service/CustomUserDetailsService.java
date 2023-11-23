package com.example.equipment_booker.security.service;

import com.example.equipment_booker.model.CompanyAdministrator;
import com.example.equipment_booker.model.RegisteredUser;
import com.example.equipment_booker.model.SystemAdministrator;
import com.example.equipment_booker.service.CompanyAdministratorService;
import com.example.equipment_booker.service.RegisteredUserService;
import com.example.equipment_booker.service.SystemAdministratorService;
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
    private final CompanyAdministratorService companyAdministratorService;
    private final SystemAdministratorService systemAdministratorService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        RegisteredUser registeredUser = registeredUserService.findByEmail(username);
        CompanyAdministrator companyAdministrator = companyAdministratorService.findByEmail(username);
        SystemAdministrator systemAdministrator = systemAdministratorService.findByEmail(username);
        if (registeredUser == null && companyAdministrator == null && systemAdministrator == null) {
            throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username));
        } else {
            return new UserDetails() {
                @Override
                public Collection<? extends GrantedAuthority> getAuthorities() {
                    var authorities = new ArrayList<GrantedAuthority>();
                    if (registeredUser != null) {
                        authorities.add(new SimpleGrantedAuthority("ROLE_REGISTERED_USER"));
                    } else if (companyAdministrator != null) {
                        authorities.add(new SimpleGrantedAuthority("ROLE_COMPANY_ADMINISTRATOR"));
                    } else {
                        authorities.add(new SimpleGrantedAuthority("ROLE_SYSTEM_ADMINISTRATOR"));
                    }

                    return authorities;
                }

                @Override
                public String getPassword() {
                    if (registeredUser != null) {
                        return registeredUser.getPassword();
                    } else if (companyAdministrator != null) {
                        return companyAdministrator.getPassword();
                    } else {
                        return systemAdministrator.getPassword();
                    }
                }

                @Override
                public String getUsername() {
                    if (registeredUser != null) {
                        return registeredUser.getEmail();
                    } else if (companyAdministrator != null) {
                        return companyAdministrator.getEmail();
                    } else {
                        return systemAdministrator.getEmail();
                    }
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
