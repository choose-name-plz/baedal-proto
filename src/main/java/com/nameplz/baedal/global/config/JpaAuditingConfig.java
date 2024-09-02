package com.nameplz.baedal.global.config;

import com.nameplz.baedal.global.common.security.UserDetailsImpl;
import java.util.Optional;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Configuration
@EnableJpaAuditing
public class JpaAuditingConfig implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return Optional.empty();
        }

        // 익명 사용자(anonymousUser)에 대해 알아보가
        if (authentication.getPrincipal() instanceof String) {
            return Optional.of((String) authentication.getPrincipal());
        }
    
        return Optional.of(((UserDetailsImpl) authentication.getPrincipal()).getUsername());
    }

}
