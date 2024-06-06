package fr.mathieu.rueduterroir.configuration.auditor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "springSecurityAuditorAware")
public class AuditingConfig {

    @Bean
    public AuditorAware<Object> springSecurityAuditorAware() {
        return new SpringSecurityAuditorAware();
    }
}
