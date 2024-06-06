package fr.mathieu.rueduterroir.configuration.auditor;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class SpringSecurityAuditorAware implements AuditorAware<Object> {
    @Override
    public Optional<Object> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()){
            return Optional.of(authentication.getPrincipal());
        }

        return Optional.empty();
    }
}
