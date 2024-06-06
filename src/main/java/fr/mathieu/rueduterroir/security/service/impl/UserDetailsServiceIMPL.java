package fr.mathieu.rueduterroir.security.service.impl;

import fr.mathieu.rueduterroir.model.entity.Role;
import fr.mathieu.rueduterroir.model.entity.Tenant;
import fr.mathieu.rueduterroir.service.ITenantService;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserDetailsServiceIMPL implements UserDetailsService {
    private final ITenantService tenantService;

    public UserDetailsServiceIMPL(ITenantService tenantService) {
        this.tenantService = tenantService;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Tenant> tenant = tenantService.getByEmail(email);
        if (tenant.isEmpty()) {
            throw new UsernameNotFoundException("User not found with email : " + email);
        }
        if (Boolean.FALSE.equals(tenant.get().getIsEnabled())){
            throw new DisabledException("User account not enabled");
        }

        return User.builder()
                .username(tenant.get().getEmail())
                .password(tenant.get().getPassword())
                .authorities(getGrantedAuthorities(tenant.get().getRoles()))
                .build();
    }

    private Set<GrantedAuthority> getGrantedAuthorities(List<Role> roles) {
        Set<GrantedAuthority> authorities = new HashSet<>();
        roles.forEach(role -> authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName())));
        return authorities;
    }
}

